package com.accenture.repository.entity.vehicules;


import com.accenture.model.Permis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehicule  {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String marque;
   private String modele;
   private String couleur;


   private double tarifJournalier;
   private int kilometrage;
   private boolean retireDuParc;
   private boolean actif;
}
