package com.danielantolin.portfolio.dto;

import java.util.List;

public record ProfileDto(
        String fullName,
        String title,
        String summary,
        List<String> focusAreas
) {
}
