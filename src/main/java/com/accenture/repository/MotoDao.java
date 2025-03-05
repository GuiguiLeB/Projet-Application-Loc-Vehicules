package com.accenture.repository;

import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoDao extends JpaRepository<Moto, Integer> {
    Optional<Moto> findById(int id);
}
