package com.danielantolin.portfolio.dto;

import java.util.List;

public record ExperienceDto(
        String company,
        String role,
        String period,
        String employmentType,
        List<String> highlights,
        List<String> technologies
) {
}
