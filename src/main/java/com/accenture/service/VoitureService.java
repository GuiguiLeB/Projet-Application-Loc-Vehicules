package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface VoitureService {

    VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException;
    List<VoitureResponseDto> listerVoiture();
    VoitureResponseDto modifierVoiture(int id, VoitureRequestDto voitureRequestDto) throws VoitureException, EntityNotFoundException;
    VoitureResponseDto retirerDuParc(int id) throws EntityNotFoundException;
    List<VoitureResponseDto> filtrer(Filtre filtre);
    VoitureResponseDto trouverVoiture(int id);
    VoitureResponseDto recupererInfosVoiture(int id, boolean actif);
}
