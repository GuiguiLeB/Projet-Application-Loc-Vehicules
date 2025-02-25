package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

public interface ClientService {
    ClientResponseDto ajouterClient(ClientRequestDto clientRequestDto) throws ClientException;
    ClientResponseDto trouver(String email);
    ClientResponseDto recupInfos(String email, String password);
    List<ClientResponseDto> listeClients();
    ClientResponseDto modifierClient(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;
    void supprimerClient(String email,String password) throws EntityNotFoundException;
    void verifierClient(ClientRequestDto clientRequestDto) throws ClientException;

}
