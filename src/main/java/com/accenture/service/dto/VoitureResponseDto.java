package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.TypeVoiture;

public record VoitureResponseDto(

        String marque,
        String modele,
        String couleur,
        int nbPlaces,
        Carburant carburant,
        int nbPortes,
        String transmission,
        boolean climatisation,
        int nbBagages,
        TypeVoiture type,
        Permis permis,
        double tarifJournalier,
        int kilometrage,
        boolean actif,
        boolean retireDuParc

) {
}
