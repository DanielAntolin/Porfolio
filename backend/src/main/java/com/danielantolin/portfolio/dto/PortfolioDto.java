package com.danielantolin.portfolio.dto;

import java.util.List;

public record PortfolioDto(
        ProfileDto profile,
        List<ExperienceDto> experience,
        List<EducationDto> education,
        List<SkillGroupDto> skills,
        List<ProjectDto> projects,
        List<LanguageDto> languages,
        ContactDto contact
) {
}
