package com.accenture.service;

import com.accenture.exception.MotoException;

import com.accenture.model.Filtre;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MotoService {

    MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto) throws MotoException;
    List<MotoResponseDto> listerMoto();
    MotoResponseDto modifierMoto(int id, MotoRequestDto motoRequestDto) throws MotoException, EntityNotFoundException;
    MotoResponseDto retirerDuParc(int id) throws EntityNotFoundException;
    List<MotoResponseDto> filtrer(Filtre filtre);
    MotoResponseDto trouverMoto(int id);
    MotoResponseDto recupererInfosMoto(int id, boolean actif);
}