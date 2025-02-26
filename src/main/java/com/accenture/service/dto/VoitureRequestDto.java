package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeVoiture;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VoitureRequestDto(

        @NotBlank
        @NotNull
        String marque,

        @NotBlank
        @NotNull
        String modele,

        @NotNull
        @NotBlank
        String couleur,

        @NotNull
        Integer nbPlaces,

        @NotBlank
        Carburant typeCarburant,

        @NotNull
        Integer nbPortes,

        @NotNull
        Transmission transmission,

        boolean climatisation,

        @NotNull
        Integer nbBagages,

        @NotNull
        TypeVoiture typeVoiture,

        @NotNull
        Permis permis,

        @NotNull
        double tarifJournalier,

        @NotNull
        int kilometrage,


        boolean retireDuParc,

        boolean actif


) {
}
