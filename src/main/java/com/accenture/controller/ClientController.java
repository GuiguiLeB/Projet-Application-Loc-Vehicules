package com.accenture.controller;


import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping("/{email}")
    @Operation(summary = "Trouver un client", description = "Trouve un client.")
    @ApiResponse(responseCode = "201", description = "Client trouvé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<ClientResponseDto> trouverUnClient(@PathVariable("email") String email, String password){

        ClientResponseDto trouve = clientService.trouverClient(email, password);
        return ResponseEntity.ok(trouve);
    }
    @GetMapping
    @Operation(summary = "Afficher tous les clients", description = "Affiche tous les clients de l'application.")
    @ApiResponse(responseCode = "201", description = "Affiche tous les clients avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<ClientResponseDto>trouverTousLesClients(){
        return clientService.listerClients();
    }


    @PostMapping
    @Operation(summary = "Ajouter un client", description = "Ajoute un client dans l'application.")
    @ApiResponse(responseCode = "201", description = "Client ajouté avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterClient(@RequestBody ClientRequestDto clientRequestDto) {
      clientService.ajouterClient(clientRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Supprimer un client", description = "Supprime un client dans l'application.")
    @ApiResponse(responseCode = "201", description = "Client supprimé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerClient(@PathVariable("email") String email, String password) {
        clientService.supprimerClient(email,password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{email}")
    @Operation(summary = "Modifier un client", description = "Permet de modifier les informations d'un client.")
    @ApiResponse(responseCode = "201", description = "Client modifié avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<ClientResponseDto>modifierClient(@PathVariable("email")String email,@RequestParam String password, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifierClient(email, password, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }


    @GetMapping("/informations")
    ResponseEntity<ClientResponseDto> infosClient(String email, String password){
        ClientResponseDto clientInfo = clientService.recupererInfos(email, password);
        return ResponseEntity.ok(clientInfo);}
    }






