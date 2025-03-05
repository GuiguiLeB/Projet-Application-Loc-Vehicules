package com.accenture.controller;

import com.accenture.model.Filtre;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.ClientService;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
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
@RequestMapping("/voitures")
public class VoitureController {

    private final VoitureService voitureService;


    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une voiture", description = "Trouve une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture trouvée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<VoitureResponseDto> trouverUneVoiture(@PathVariable("id")int id){
        VoitureResponseDto trouve = voitureService.trouverVoiture(id);
        return ResponseEntity.ok(trouve);
    }

    @GetMapping
    @Operation(summary = "Afficher toutes les voitures", description = "Affiche toutes les voitures du parc.")
    @ApiResponse(responseCode = "201", description = "Affiche toutes les voitures avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<VoitureResponseDto> afficherToutesVoitures(){
        return voitureService.listerVoiture();
    }


    @PostMapping
    @Operation(summary = "Ajouter une voiture", description = "Ajoute une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture ajoutée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterVoiture(@RequestBody VoitureRequestDto voitureRequestDto) {
        voitureService.ajouterVoiture(voitureRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une voiture", description = "Supprime une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture supprimée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerVoiture(@PathVariable("id") int id) {
        voitureService.retirerDuParc(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une voiture", description = "Permet de modifier une voiture dans le parc.")
    @ApiResponse(responseCode = "201", description = "Voiture modifiée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<VoitureResponseDto>modifierVoiture(@PathVariable("id")int id, @RequestBody @Valid VoitureRequestDto voitureRequestDto) {
        VoitureResponseDto reponse = voitureService.modifierVoiture(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/filtre")
    @Operation(summary = "Filtrage des voitures", description = "Filtre les voitures présentes dans le parc :" +        " actif, inactif, dans le parc et hors du parc ")
    @ApiResponse(responseCode = "201", description = "Voitures filtrées avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<List<VoitureResponseDto>> filtrer(@RequestParam Filtre filtre) {
        List<VoitureResponseDto> voitures = voitureService.filtrer(filtre);
        return ResponseEntity.ok(voitures);}


}
