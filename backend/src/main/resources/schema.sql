CREATE TABLE IF NOT EXISTS contact_verification (
    email_hash VARCHAR(64) PRIMARY KEY,
    code_hash VARCHAR(64) NOT NULL,
    verification_token_hash VARCHAR(64),
    expires_at TIMESTAMP NOT NULL,
    verified_at TIMESTAMP,
    attempts INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS contact_daily_limit (
    subject_hash VARCHAR(64) NOT NULL,
    limit_date DATE NOT NULL,
    submission_count INT NOT NULL,
    PRIMARY KEY (subject_hash, limit_date)
);
