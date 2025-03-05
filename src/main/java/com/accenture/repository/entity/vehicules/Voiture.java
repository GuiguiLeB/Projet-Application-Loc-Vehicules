package com.accenture.repository.entity.vehicules;

import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeVoiture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Voiture")
@EqualsAndHashCode(callSuper = true)

public class Voiture extends Vehicule {


    private int nbPlaces;
    private int nbBagages;
    private Boolean climatisation;
    private int nbPortes;

    @Enumerated(EnumType.STRING)
    private Carburant typeCarburant;

    @Enumerated(EnumType.STRING)
    private TypeVoiture typeVoiture;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private Permis permis;

    public Voiture(int id, String marque, String modele, String couleur, double tarifJournalier, int kilometrage, boolean retireDuParc, boolean actif, int nbPlaces, int nbBagages, Boolean climatisation, int nbPortes, Carburant typeCarburant, TypeVoiture typeVoiture, Transmission transmission) {
        super(id, marque, modele, couleur, tarifJournalier, kilometrage, retireDuParc, actif);
        this.nbPlaces = nbPlaces;
        this.nbBagages = nbBagages;
        this.climatisation = climatisation;
        this.nbPortes = nbPortes;
        this.typeCarburant = typeCarburant;
        this.typeVoiture = typeVoiture;
        this.transmission = transmission;
    }





//    public String getPermisRequis() {
//        if (nbPlaces <= 9) {
//            return "Permis B";
//        } else if (nbPlaces <= 16) {
//            return "Permis D1";
//        } else {
//            return "Permis non défini pour ce nombre de places";
//        }
//    }



}
