package com.accenture.repository;

import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientDao extends JpaRepository<Client, Integer> {

}
