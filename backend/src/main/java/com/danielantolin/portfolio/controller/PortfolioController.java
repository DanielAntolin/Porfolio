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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/portfolio")
    public PortfolioDto getPortfolio(@RequestParam(defaultValue = "es") String lang) {
        return portfolioService.getPortfolio(lang);
    }
}
