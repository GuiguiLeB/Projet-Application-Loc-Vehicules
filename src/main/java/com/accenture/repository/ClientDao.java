package com.accenture.repository;

import com.accenture.model.Permis;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, String> {


    Optional<Client> findByEmail(String email);
    List<Client> findByNom(String nom);
    List<Client> findByPrenom(String prenom);
    Optional<Client> findByEmailAndPassword(String email,String password);
    List<Client> findByAdresse(Adresse adresse);
    List<Client> findBydateNaissance(LocalDate dateInscription);
    List<Client> findByPermis(List<Permis> permis);



}
