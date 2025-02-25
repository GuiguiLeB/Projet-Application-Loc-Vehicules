package com.accenture.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminRequestDto(

        @NotBlank
        String nom,

        @NotBlank
        String prenom,

        @NotBlank
        String fonction,

        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        String password
) {
}
