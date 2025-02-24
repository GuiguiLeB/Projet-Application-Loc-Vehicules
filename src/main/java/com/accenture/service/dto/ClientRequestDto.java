package com.accenture.service.dto;

import com.accenture.model.Permis;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ClientRequestDto(

        @NotBlank
        String nom,

        @NotBlank
        String prenom,

        @NotNull
        AdresseDto adresse,

        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        String password,

        @Past
        LocalDate dateNaissance,

        List<Permis> permis

) {
}
