package com.danielantolin.portfolio.controller;

import com.danielantolin.portfolio.dto.ContactDto;
import com.danielantolin.portfolio.dto.ContactRequestDto;
import com.danielantolin.portfolio.dto.EmailVerificationConfirmDto;
import com.danielantolin.portfolio.dto.EmailVerificationRequestDto;
import com.danielantolin.portfolio.dto.EmailVerificationResponseDto;
import com.danielantolin.portfolio.dto.EducationDto;
import com.danielantolin.portfolio.dto.ExperienceDto;
import com.danielantolin.portfolio.dto.LanguageDto;
import com.danielantolin.portfolio.dto.PortfolioDto;
import com.danielantolin.portfolio.dto.ProfileDto;
import com.danielantolin.portfolio.dto.ProjectDto;
import com.danielantolin.portfolio.dto.SkillGroupDto;
import com.danielantolin.portfolio.service.PortfolioService;
import com.danielantolin.portfolio.service.ContactEmailService;
import com.danielantolin.portfolio.service.ContactSecurityService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final ContactEmailService contactEmailService;
    private final ContactSecurityService contactSecurityService;

    public PortfolioController(PortfolioService portfolioService, ContactEmailService contactEmailService,
                               ContactSecurityService contactSecurityService) {
        this.portfolioService = portfolioService;
        this.contactEmailService = contactEmailService;
        this.contactSecurityService = contactSecurityService;
    }

    @GetMapping("/profile")
    public ProfileDto getProfile(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getProfile(lang);
    }

    @GetMapping("/experience")
    public List<ExperienceDto> getExperience(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getExperience(lang);
    }

    @GetMapping("/education")
    public List<EducationDto> getEducation(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getEducation(lang);
    }

    @GetMapping("/skills")
    public List<SkillGroupDto> getSkills(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getSkills(lang);
    }

    @GetMapping("/projects")
    public List<ProjectDto> getProjects(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getProjects(lang);
    }

    @GetMapping("/languages")
    public List<LanguageDto> getLanguages(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getLanguages(lang);
    }

    @GetMapping("/contact")
    public ContactDto getContact(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getContact(lang);
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> sendContact(@Valid @RequestBody ContactRequestDto request, HttpServletRequest httpRequest) {
        if (request.website() != null && !request.website().isBlank()) {
            return ResponseEntity.noContent().build();
        }
        contactSecurityService.authorizeAndConsume(request.email(), request.verificationToken(), httpRequest);
        contactEmailService.send(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contact/verification")
    public ResponseEntity<Void> requestEmailVerification(@Valid @RequestBody EmailVerificationRequestDto request,
                                                          HttpServletRequest httpRequest) {
        contactSecurityService.requestVerification(request, httpRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contact/verification/confirm")
    public EmailVerificationResponseDto confirmEmailVerification(@Valid @RequestBody EmailVerificationConfirmDto request) {
        return contactSecurityService.confirmVerification(request);
    }

    @GetMapping("/portfolio")
    public PortfolioDto getPortfolio(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getPortfolio(lang);
    }
}
