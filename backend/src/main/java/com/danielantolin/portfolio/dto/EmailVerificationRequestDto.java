package com.danielantolin.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailVerificationRequestDto(
        @NotBlank @Email @Size(max = 254) String email,
        @Size(max = 200) String website
) {
}
