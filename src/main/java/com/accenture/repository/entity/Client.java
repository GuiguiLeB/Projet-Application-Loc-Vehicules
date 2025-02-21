package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor

public class Client extends UtilisateurConnecte {



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    private LocalDate dateNaissance;
    private LocalDate dateInscription;
    private List<String> permis;
    private Boolean desactive;
}
