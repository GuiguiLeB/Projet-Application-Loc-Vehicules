package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrateurDao extends JpaRepository<Administrateur, String> {

    Optional<Administrateur> findByEmailContaining(String email);
}
