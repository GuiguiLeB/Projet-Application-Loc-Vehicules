package com.accenture.repository.entity;

import com.accenture.model.Carburant;
import com.accenture.model.Transmission;
import com.accenture.model.TypeVoiture;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Voiture {

    @Id
    private String marque;
    private String modele;
    private String couleur;
    private int nbPlaces;
    private int nbPortes;
    private int nbBagages;
    private Boolean climatisation;

    //********renseignements privés*********

    private double tarifJournalier;
    private int kilometrage;
    private boolean actif;
    private boolean retireDuParc;

    @Enumerated(EnumType.STRING)
    private Carburant typeCarburant;

    @Enumerated(EnumType.STRING)
    private TypeVoiture typeVoiture;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    public String getPermisRequis() {
        if (nbPlaces <= 9) {
            return "Permis B";
        } else if (nbPlaces <= 16) {
            return "Permis D1";
        } else {
            return "Permis non défini pour ce nombre de places";
        }
    }



}
