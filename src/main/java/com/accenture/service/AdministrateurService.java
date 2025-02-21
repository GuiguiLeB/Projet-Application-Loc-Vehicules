package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdministrateurService {
    AdminResponseDto trouverAdmin(int id) throws AdministrateurException;

    List<AdminResponseDto> listeAdmin();

    AdminResponseDto ajouterAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException;

    void supprimerAdmin(int id) throws EntityNotFoundException;

}
