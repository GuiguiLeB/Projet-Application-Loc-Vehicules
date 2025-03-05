package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UtilisateurConnecte {


    @Id
    private String email;
    private String nom;
    private String prenom;
    private String password;

}
