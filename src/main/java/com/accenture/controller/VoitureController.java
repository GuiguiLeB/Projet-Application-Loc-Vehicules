package com.accenture.controller;

import com.accenture.exception.ClientException;
import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
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
@RequestMapping("/voitures")
public class VoitureController {

    private final VoitureService voitureService;
    private static final Logger logger = LoggerFactory.getLogger(VoitureController.class);


    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une voiture", description = "Trouve une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture trouvée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<VoitureResponseDto> trouverUneVoiture(@PathVariable("id")int id){
        try {
            VoitureResponseDto trouve = voitureService.trouverVoiture(id);
            logger.info("La voiture a bien été trouvée");
            return ResponseEntity.ok(trouve);
        } catch (VoitureException e) {
            logger.error("Erreur lors de la recherche de la voiture");
            throw new VoitureException(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Afficher toutes les voitures", description = "Affiche toutes les voitures du parc.")
    @ApiResponse(responseCode = "201", description = "Affiche toutes les voitures avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<VoitureResponseDto> afficherToutesVoitures(){
        try {
            List<VoitureResponseDto> listeVoitures = voitureService.listerVoiture();
            logger.info("La liste des voitures a bien été recupérée");
            return listeVoitures;
        } catch (VoitureException e) {
            logger.error("Erreur lors de la récupération de la liste des voitures");
            throw new VoitureException(e.getMessage());
        }
    }


    @PostMapping
    @Operation(summary = "Ajouter une voiture", description = "Ajoute une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture ajoutée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterVoiture(@RequestBody VoitureRequestDto voitureRequestDto) {
        try {
            voitureService.ajouterVoiture(voitureRequestDto);
            logger.info("La voiture a bien été ajoutée");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (VoitureException e) {
            logger.error("Erreur lors de l'ajout de la voiture");
            throw new VoitureException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une voiture", description = "Supprime une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture supprimée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerVoiture(@PathVariable("id") int id) {
        try {
            voitureService.retirerDuParc(id);
            logger.info("La voiture a bien été supprimée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (VoitureException e) {
            logger.error("Erreur lors de la suppression de la voiture");
            throw new VoitureException(e.getMessage());
        }
    }


    @GetMapping("/filtre")
    @Operation(summary = "Filtrage des voitures", description = "Filtre les voitures présentes dans le parc :" +        " actif, inactif, dans le parc et hors du parc ")
    @ApiResponse(responseCode = "201", description = "Voitures filtrées avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<List<VoitureResponseDto>> filtrer(@RequestParam Filtre filtre) {
        List<VoitureResponseDto> voitures = voitureService.filtrer(filtre);
        return ResponseEntity.ok(voitures);}

    @PatchMapping("/{id}")
    @Operation(summary = "Modifier les voitures", description = "Modifie un ou plusieurs attributs des voitures présentes dans le parc ")
    @ApiResponse(responseCode = "201", description = "Voiture modifiée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<VoitureResponseDto>modifierPartiellement(@PathVariable("id")int id, @RequestBody VoitureRequestDto voitureRequestDto){
        VoitureResponseDto reponse = voitureService.modifierPartiellement(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }


}
