package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeVoiture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Detail des vehicules")
public record VoitureRequestDto(

        @Schema(description = "Marque du véhicule", example = "Toyota")
        @NotBlank
        @NotNull
        String marque,

        @Schema(description = "Modèle du véhicule", example = "Corolla")
        @NotBlank
        @NotNull
        String modele,


        @Schema(description = "Couleur du véhicule", example = "Rouge")
        @NotNull
        @NotBlank
        String couleur,

        @Schema(description = "Nombre de places", example = "5")
        @NotNull
        Integer nbPlaces,



        @Schema(description = "Type de carburant")
        @NotBlank
        Carburant typeCarburant,

        @Schema(description = "Nombre de portes", example = "5")
        @NotNull
        Integer nbPortes,


        @Schema(description = "Transmission du véhicule")
        @NotNull
        Transmission transmission,

        @Schema(description = "Climatisation disponible", example = "true")
        boolean climatisation,

        @Schema(description = "Nombre de bagages", example = "1")
        @NotNull
        Integer nbBagages,

        @Schema(description = "Type de voiture")
        @NotNull
        TypeVoiture typeVoiture,

        @Schema(description = "Permis")
        @NotNull
        Permis permis,

        @Schema(description = "Tarif journalier", example = "150.0")
        @NotNull
        double tarifJournalier,

        @Schema(description = "Kilométrage actuel", example = "15000")
        @NotNull
        int kilometrage,


        @Schema(description = "Retiré du parc", example = "false")
        boolean retireDuParc,

        @Schema(description = "Actif", example = "true")
        boolean actif


) {
}
