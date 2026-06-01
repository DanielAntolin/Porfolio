package com.danielantolin.portfolio.controller;

import com.danielantolin.portfolio.dto.ContactDto;
import com.danielantolin.portfolio.dto.EducationDto;
import com.danielantolin.portfolio.dto.ExperienceDto;
import com.danielantolin.portfolio.dto.LanguageDto;
import com.danielantolin.portfolio.dto.PortfolioDto;
import com.danielantolin.portfolio.dto.ProfileDto;
import com.danielantolin.portfolio.dto.ProjectDto;
import com.danielantolin.portfolio.dto.SkillGroupDto;
import com.danielantolin.portfolio.service.PortfolioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/profile")
    public ProfileDto getProfile() {
        return portfolioService.getProfile();
    }

    @GetMapping("/experience")
    public List<ExperienceDto> getExperience() {
        return portfolioService.getExperience();
    }

    @GetMapping("/education")
    public List<EducationDto> getEducation() {
        return portfolioService.getEducation();
    }

    @GetMapping("/skills")
    public List<SkillGroupDto> getSkills() {
        return portfolioService.getSkills();
    }

    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {
        return portfolioService.getProjects();
    }

    @GetMapping("/languages")
    public List<LanguageDto> getLanguages() {
        return portfolioService.getLanguages();
    }

    @GetMapping("/contact")
    public ContactDto getContact() {
        return portfolioService.getContact();
    }

    @GetMapping("/portfolio")
    public PortfolioDto getPortfolio() {
        return portfolioService.getPortfolio();
    }
}
