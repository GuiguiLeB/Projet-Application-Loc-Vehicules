package com.accenture.repository.entity.vehicules;

import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeMoto;
import com.accenture.model.TypeVoiture;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Moto")
@EqualsAndHashCode(callSuper = true)

public class Moto extends Vehicule {


    private int nbCylindres;
    private int cylindree;
    private int poids;




    private int puissanceKw;
    private int hauteurSelle;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private TypeMoto typeMoto;

    @Enumerated(EnumType.STRING)
    private Permis permis;


public Moto(int id, String marque, String modele, String couleur, double tarifJournalier, int kilometrage,
            boolean retireDuParc, boolean actif, int nbCylindres, int cylindree, int poids,
            int puissanceKw, int hauteurSelle, Transmission transmission, TypeMoto typeMoto) {
    super(id, marque, modele, couleur, tarifJournalier, kilometrage, retireDuParc, actif);
    this.nbCylindres = nbCylindres;
    this.cylindree = cylindree;
    this.poids = poids;
    this.puissanceKw = puissanceKw;
    this.hauteurSelle = hauteurSelle;
    this.transmission = transmission;
    this.typeMoto = typeMoto;
}
}