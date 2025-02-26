package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface VoitureService {

    VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException;
    List<VoitureResponseDto> listeVoiture();
    VoitureResponseDto modifierVoiture(int id, VoitureRequestDto voitureRequestDto) throws VoitureException, EntityNotFoundException;
    void retirerDuParc(int id) throws EntityNotFoundException;
    void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException;
    VoitureResponseDto trouverVoiture(int id);
    VoitureResponseDto recupInfosVoiture(int id, boolean actif);
}
