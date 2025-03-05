package com.accenture.service.dto;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Detail des motos")
public record MotoRequestDto(
        @Schema(description = "Marque du véhicule", example = "Kawasaki")
        @NotBlank
        @NotNull
        String marque,

        @Schema(description = "Modèle du véhicule", example = "200")
        @NotBlank
        @NotNull
        String modele,


        @Schema(description = "Couleur du véhicule", example = "Noire")
        @NotNull
        @NotBlank
        String couleur,


        @Schema(description = "Nombre de cylindres", example = "4")
        @NotNull
        Integer nbCylindres,

        @Schema(description = "Cylindrée de la moto", example = "300")
        @NotNull
        Integer cylindree,

        @Schema(description = "Poids de la moto", example = "400")
        @NotNull
        Integer poids,


        @Schema(description = "Puissance de la moto", example = "22")
        @NotNull
        Integer puissanceKw,


        @NotNull
        Integer hauteurSelle,

        @Schema(description = "Transmission de la moto")
        @NotNull
        Transmission transmission,

        @Schema(description = "Type de moto")
        @NotNull
        TypeMoto typeMoto,

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
