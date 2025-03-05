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
    Optional<Client> findByEmailAndPassword(String email,String password);


}
