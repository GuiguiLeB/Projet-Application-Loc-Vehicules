package com.accenture.controller;


import com.accenture.exception.AdministrateurException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.AdministrateurService;
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
@RequestMapping("/administrateurs")
public class AdministrateurController {

    private final AdministrateurService administrateurService;
    private static final Logger logger = LoggerFactory.getLogger(AdministrateurController.class);

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    @GetMapping("/{email}")
    @Operation(summary = "Trouver un administrateur", description = "Trouve un administrateur.")
    @ApiResponse(responseCode = "201", description = "Administrateur trouvé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<AdminResponseDto> trouverUnAdmin(@PathVariable("email") String email, String password){

        try {
            AdminResponseDto trouveAdm = administrateurService.trouverAdmin(email,password);
            logger.info("L'administrateur a bien été trouvé");
            return ResponseEntity.ok(trouveAdm);
        } catch (AdministrateurException e) {
            logger.error("Erreur lors de la récupération de l'administrateur");
            throw new AdministrateurException(e.getMessage());
        }
    }
    @GetMapping
    @Operation(summary = "Afficher tous les administrateurs", description = "Affiche tous les admnistrateurs de l'application.")
    @ApiResponse(responseCode = "201", description = "Affiche tous les administrateurs avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<AdminResponseDto> trouverTousLesAdmin(){

        try {
            List<AdminResponseDto> listeAdmins = administrateurService.listerAdmin();
            logger.info("La liste des administrateurs a bien été recupérée");
            return listeAdmins;
        } catch (AdministrateurException e) {
            logger.error("Erreur lors de la récupération de la liste des administrateurs");
            throw new AdministrateurException(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Ajouter un administrateur", description = "Ajoute un administrateur dans l'application.")
    @ApiResponse(responseCode = "201", description = "Administrateur ajouté avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        try {
            administrateurService.ajouterAdmin(adminRequestDto);
            logger.info("L'administrateur' a bien été ajouté");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AdministrateurException e) {
            logger.error("Erreur lors de l'ajout de l'administrateur");
            throw new AdministrateurException(e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Supprimer un administrateur", description = "Supprime un administrateur dans l'application.")
    @ApiResponse(responseCode = "201", description = "Administrateur supprimé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerAdmin(@PathVariable("email") String email, String password) {
        try {
            administrateurService.supprimerAdmin(email,password);
            logger.info("L'administrateur a bien été supprimé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la suppression de l'administrateur");
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{email}")
    @Operation(summary = "Modifier un administrateur", description = "Permet de modifier les informations d'un administrateur.")
    @ApiResponse(responseCode = "201", description = "Administrateur modifié avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<AdminResponseDto>modifierAdmin(@PathVariable("email")String email,@RequestParam String password, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto reponse = administrateurService.modifierAdmin(email,password, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }


}
