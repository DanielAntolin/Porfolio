package com.danielantolin.portfolio.dto;

import java.util.List;

public record ProjectDto(
        String name,
        String description,
        String period,
        List<String> technologies,
        String url,
        String primaryLanguage,
        int stars
) {
}
