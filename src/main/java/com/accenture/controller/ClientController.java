package com.accenture.controller;


import com.accenture.exception.ClientException;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{email}")
    @Operation(summary = "Trouver un client", description = "Trouve un client.")
    @ApiResponse(responseCode = "201", description = "Client trouvé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<ClientResponseDto> trouverUnClient(@PathVariable("email") String email, String password) {

        try {
            ClientResponseDto trouve = clientService.trouverClient(email, password);
            logger.info("Le client a bien été trouvé");
            return ResponseEntity.ok(trouve);
        } catch (ClientException e) {
            logger.error("Erreur lors de la récupération du client");
            throw new ClientException(e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "Afficher tous les clients", description = "Affiche tous les clients de l'application.")
    @ApiResponse(responseCode = "201", description = "Affiche tous les clients avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<ClientResponseDto> trouverTousLesClients() {
        try {
            List<ClientResponseDto> listeClients = clientService.listerClients();
            logger.info("La liste des clients a bien été recupérée");
            return listeClients;
        } catch (ClientException e) {
            logger.error("Erreur lors de la récupération de la liste des clients");
            throw new ClientException(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Ajouter un client", description = "Ajoute un client dans l'application.")
    @ApiResponse(responseCode = "201", description = "Client ajouté avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterClient(@RequestBody ClientRequestDto clientRequestDto) {
        try {
            clientService.ajouterClient(clientRequestDto);
            logger.info("Le client a bien été ajouté");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ClientException e) {
            logger.error("Erreur lors de l'ajout du client");
            throw new ClientException(e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Supprimer un client", description = "Supprime un client dans l'application.")
    @ApiResponse(responseCode = "201", description = "Client supprimé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerClient(@PathVariable("email") String email, String password) {
        try {
            clientService.supprimerClient(email, password);
            logger.info("Le client a bien été supprimé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ClientException e) {
            logger.error("Erreur lors de la suppression du client");
            throw new ClientException(e.getMessage());
        }
    }

    @PutMapping("/{email}")
    @Operation(summary = "Modifier un client", description = "Permet de modifier les informations d'un client.")
    @ApiResponse(responseCode = "201", description = "Client modifié avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<ClientResponseDto> modifierClient(@PathVariable("email") String email, @RequestParam String password, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        try {
            ClientResponseDto reponse = clientService.modifierClient(email, password, clientRequestDto);
            logger.info("Le client a bien été modifié");
            return ResponseEntity.ok(reponse);
        } catch (ClientException e) {
            logger.error("Erreur lors de la modification du client");
            throw new ClientException(e.getMessage());
        }
    }
}







