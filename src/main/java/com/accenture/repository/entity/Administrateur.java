package com.accenture.repository.entity;


import com.accenture.repository.AdministrateurDao;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Administrateur extends UtilisateurConnecte{

    private String fonction;

}
