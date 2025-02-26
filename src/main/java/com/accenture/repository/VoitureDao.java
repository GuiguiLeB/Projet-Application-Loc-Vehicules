package com.accenture.repository;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.vehicules.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoitureDao extends JpaRepository <Voiture, Integer>{
    Optional<Voiture> findById(int id);
}

