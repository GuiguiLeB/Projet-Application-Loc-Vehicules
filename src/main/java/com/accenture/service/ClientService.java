package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClientService {
    ClientResponseDto ajouterClient(ClientRequestDto clientRequestDto) throws ClientException;
    ClientResponseDto trouver(int id);
    List<ClientResponseDto> listeClients();
    void supprimerClient(int id) throws EntityNotFoundException;

}
