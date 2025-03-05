package com.accenture.service.dto;

import com.accenture.model.Permis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Détails d'un client")
public record ClientRequestDto(

        @Schema(description = "Nom de famille du client", example = "Dupont")
        @NotBlank
        String nom,

        @Schema(description = "Prénom du client", example = "Jean")
        @NotBlank
        String prenom,

        @Schema(description = "Adresse du client")
        @NotNull
        AdresseDto adresse,

        @Schema(description = "Adresse e-mail du client")
        @NotBlank
        @NotNull
        String email,

        @Schema(description = "Mot de passe du client")
        @NotBlank
        @NotNull
        String password,


        @Schema(description = "Date de naissance du client")
        @Past
        LocalDate dateNaissance,

        @Schema(description = "Liste des permis détenus par le client")
        List<Permis> permis

) {
}
