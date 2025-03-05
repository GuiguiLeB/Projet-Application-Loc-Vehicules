package com.accenture.service.dto;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Detail de la moto")
public record MotoResponseDto(

        int id,

        @Schema(description = "Marque du véhicule", example = "Kawasaki")
        String marque,

        @Schema(description = "Modèle du véhicule", example = "z900")
        String modele,


        @Schema(description = "Couleur du véhicule", example = "Noire")
        String couleur,


        @Schema(description = "Nombre de cylindres", example = "4")
        int nbCylindres,

        @Schema(description = "Cylindrée de la moto", example = "300")
        int cylindree,

        @Schema(description = "Poids de la moto", example = "400")
        int poids,


        @Schema(description = "Puissance de la moto", example = "22")
        int puissanceKw,

        @Schema(description = "Hauteur de la selle")
        int hauteurSelle,

        @Schema(description = "Transmission de la moto")
        Transmission transmission,

        @Schema(description = "Type de moto")
        TypeMoto typeMoto,

        @Schema(description = "Permis")
        Permis permis,

        @Schema(description = "Tarif journalier", example = "150.0")
        double tarifJournalier,

        @Schema(description = "Kilométrage actuel", example = "15000")
        int kilometrage,


        @Schema(description = "Retiré du parc", example = "false")
        boolean retireDuParc,

        @Schema(description = "Actif", example = "true")
        boolean actif
) {
}
