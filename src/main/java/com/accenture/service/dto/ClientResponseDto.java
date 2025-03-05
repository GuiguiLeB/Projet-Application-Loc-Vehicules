package com.accenture.service.dto;

import com.accenture.model.Permis;
import com.accenture.repository.entity.Adresse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Détails d'un client")
public record ClientResponseDto(


        @Schema(description = "Nom de famille du client", example = "Dupont")
        String nom,

        @Schema(description = "Prénom du client", example = "Jean")
        String prenom,

        @Schema(description = "Adresse du client")
        Adresse adresse,

        @Schema(description = "Adresse e-mail du client")
        String email,

        @Schema(description = "Mot de passe du client")
        String password,


        @Schema(description = "Date de naissance du client")
        LocalDate dateNaissance,

        @Schema(description = "Date d'inscription du client")
        LocalDate dateInscription,

        @Schema(description = "Liste des permis détenus par le client")
        List<Permis>permis

) {


}
