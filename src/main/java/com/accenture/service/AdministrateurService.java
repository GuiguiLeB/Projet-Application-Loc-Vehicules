package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdministrateurService {
    AdminResponseDto trouverAdmin(String email, String password) throws AdministrateurException;
    List<AdminResponseDto> listerAdmin();
    AdminResponseDto ajouterAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException;
    void supprimerAdmin(String email) throws EntityNotFoundException;
    AdminResponseDto modifierAdmin(String email,String password, AdminRequestDto adminRequestDto) throws AdministrateurException;

}
