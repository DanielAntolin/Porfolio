package com.danielantolin.portfolio.service;

import com.danielantolin.portfolio.dto.EmailVerificationConfirmDto;
import com.danielantolin.portfolio.dto.EmailVerificationRequestDto;
import com.danielantolin.portfolio.dto.EmailVerificationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class ContactSecurityService {
    private static final int DAILY_MESSAGE_LIMIT = 2;
    private static final int DAILY_VERIFICATION_LIMIT = 3;
    private static final int MAX_CODE_ATTEMPTS = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JdbcTemplate jdbc;
    private final ContactEmailService emailService;
    private final String securitySecret;

    public ContactSecurityService(JdbcTemplate jdbc, ContactEmailService emailService,
                                  @Value("${contact.security-secret}") String securitySecret) {
        this.jdbc = jdbc;
        this.emailService = emailService;
        this.securitySecret = securitySecret;
    }

    @Transactional
    public void requestVerification(EmailVerificationRequestDto request, HttpServletRequest httpRequest) {
        if (request.website() != null && !request.website().isBlank()) return;

        String emailHash = hash(request.email().trim().toLowerCase());
        String ipHash = hash(clientIp(httpRequest));
        LocalDate today = LocalDate.now();
        if (!reserveQuota("verification-email:" + emailHash, today, DAILY_VERIFICATION_LIMIT)
                || !reserveQuota("verification-ip:" + ipHash, today, DAILY_VERIFICATION_LIMIT)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many verification requests");
        }

        String code = String.format("%06d", RANDOM.nextInt(1_000_000));
        Instant expiresAt = Instant.now().plusSeconds(15 * 60);
        jdbc.update("MERGE INTO contact_verification (email_hash, code_hash, verification_token_hash, expires_at, verified_at, attempts) "
                        + "KEY(email_hash) VALUES (?, ?, NULL, ?, NULL, 0)",
                emailHash, hash(request.email().trim().toLowerCase() + ":" + code), Timestamp.from(expiresAt));
        emailService.sendVerificationCode(request.email().trim(), code);
    }

    @Transactional
    public EmailVerificationResponseDto confirmVerification(EmailVerificationConfirmDto request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        String emailHash = hash(normalizedEmail);
        var rows = jdbc.query("SELECT code_hash, expires_at, attempts FROM contact_verification WHERE email_hash = ? FOR UPDATE",
                (resultSet, rowNum) -> new VerificationRow(
                        resultSet.getString("code_hash"),
                        resultSet.getTimestamp("expires_at").toInstant(),
                        resultSet.getInt("attempts")), emailHash);
        if (rows.isEmpty() || rows.getFirst().expiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code expired");
        }

        VerificationRow row = rows.getFirst();
        if (row.attempts() >= MAX_CODE_ATTEMPTS) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many verification attempts");
        }
        if (!MessageDigest.isEqual(row.codeHash().getBytes(StandardCharsets.UTF_8),
                hash(normalizedEmail + ":" + request.code()).getBytes(StandardCharsets.UTF_8))) {
            jdbc.update("UPDATE contact_verification SET attempts = attempts + 1 WHERE email_hash = ?", emailHash);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid verification code");
        }

        String token = randomToken();
        jdbc.update("UPDATE contact_verification SET verification_token_hash = ?, verified_at = ?, expires_at = ? WHERE email_hash = ?",
                hash(token), Timestamp.from(Instant.now()), Timestamp.from(Instant.now().plusSeconds(60 * 60)), emailHash);
        return new EmailVerificationResponseDto(token);
    }

    @Transactional
    public void authorizeAndConsume(String email, String verificationToken, HttpServletRequest httpRequest) {
        String emailHash = hash(email.trim().toLowerCase());
        Integer verified = jdbc.queryForObject("SELECT COUNT(*) FROM contact_verification WHERE email_hash = ? "
                        + "AND verification_token_hash = ? AND verified_at IS NOT NULL AND expires_at > CURRENT_TIMESTAMP",
                Integer.class, emailHash, hash(verificationToken));
        if (verified == null || verified == 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email verification required");
        }

        LocalDate today = LocalDate.now();
        if (!reserveQuota("message-email:" + emailHash, today, DAILY_MESSAGE_LIMIT)
                || !reserveQuota("message-ip:" + hash(clientIp(httpRequest)), today, DAILY_MESSAGE_LIMIT)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Daily contact limit reached");
        }
    }

    private boolean reserveQuota(String subject, LocalDate date, int limit) {
        String subjectHash = hash(subject);
        var counts = jdbc.query("SELECT submission_count FROM contact_daily_limit WHERE subject_hash = ? AND limit_date = ? FOR UPDATE",
                (resultSet, rowNum) -> resultSet.getInt(1), subjectHash, date);
        int count = counts.isEmpty() ? 0 : counts.getFirst();
        if (count >= limit) return false;
        if (counts.isEmpty()) {
            jdbc.update("INSERT INTO contact_daily_limit (subject_hash, limit_date, submission_count) VALUES (?, ?, 1)", subjectHash, date);
        } else {
            jdbc.update("UPDATE contact_daily_limit SET submission_count = submission_count + 1 WHERE subject_hash = ? AND limit_date = ?", subjectHash, date);
        }
        return true;
    }

    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded == null || forwarded.isBlank()) return request.getRemoteAddr();
        String[] addresses = forwarded.split(",");
        return addresses[addresses.length - 1].trim();
    }

    private String randomToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest((securitySecret + ":" + value).getBytes(StandardCharsets.UTF_8));
            return java.util.HexFormat.of().formatHex(digest);
        } catch (Exception exception) {
            throw new IllegalStateException("Hashing unavailable", exception);
        }
    }

    private record VerificationRow(String codeHash, Instant expiresAt, int attempts) {
    }
}
