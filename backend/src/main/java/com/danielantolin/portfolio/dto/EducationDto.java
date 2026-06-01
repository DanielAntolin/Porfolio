package com.danielantolin.portfolio.dto;

public record EducationDto(
        String institution,
        String program,
        String period,
        String status,
        String description
) {
}
