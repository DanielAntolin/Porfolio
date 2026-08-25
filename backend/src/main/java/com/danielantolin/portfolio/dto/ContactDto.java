package com.danielantolin.portfolio.dto;

import java.util.List;

public record ContactDto(
        List<SocialLinkDto> socialLinks
) {
}
