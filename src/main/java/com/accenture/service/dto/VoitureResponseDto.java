package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.TypeVoiture;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema (description = "Detail du vehicule")
public record VoitureResponseDto(

        int id,

        @Schema(description = "Marque du véhicule", example = "Toyota")
        String marque,

        @Schema(description = "Modèle du véhicule", example = "Corolla")
        String modele,

        @Schema(description = "Couleur du véhicule", example = "Rouge")
        String couleur,

        @Schema(description = "Nombre de places", example = "5")
        int nbPlaces,

        @Schema(description = "Type de carburant")
        Carburant typeCarburant,

        @Schema(description = "Nombre de portes", example = "5")
        int nbPortes,

        @Schema(description = "Transmission du véhicule")
        String transmission,

        @Schema(description = "Climatisation disponible", example = "true")
        boolean climatisation,

        @Schema(description = "Nombre de bagages", example = "1")
        int nbBagages,

        @Schema(description = "Type de voiture")
        TypeVoiture typeVoiture,

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
