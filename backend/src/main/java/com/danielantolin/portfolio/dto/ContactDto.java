package com.danielantolin.portfolio.dto;

import java.util.List;

public record ContactDto(
        String email,
        String phone,
        String location,
        List<SocialLinkDto> socialLinks
) {
}
