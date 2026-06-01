package com.danielantolin.portfolio.dto;

import java.util.List;

public record SkillGroupDto(
        String category,
        List<String> items
) {
}
