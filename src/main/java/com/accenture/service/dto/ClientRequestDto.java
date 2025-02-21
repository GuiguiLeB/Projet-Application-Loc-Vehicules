package com.accenture.service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ClientRequestDto(

        @NotBlank
        String nom,

        @NotBlank
        String prenom,

        @NotBlank
        AdresseDto adresse,

        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        String password,

        @Past
        LocalDate dateNaissance,

        @FutureOrPresent
        LocalDate dateInscription,

        List<String>permis,

        @NotNull
        Boolean desactive
) {
}
