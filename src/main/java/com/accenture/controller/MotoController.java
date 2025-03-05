package com.accenture.controller;

import com.accenture.model.Filtre;
import com.accenture.service.MotoService;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
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
@RequestMapping("/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une moto", description = "Trouve une moto dans le parc.")
    @ApiResponse(responseCode = "201", description = "Moto trouvée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<MotoResponseDto> trouverUneMoto(@PathVariable("id") int id) {
        MotoResponseDto trouve = motoService.trouverMoto(id);
        return ResponseEntity.ok(trouve);
    }

    @GetMapping
    @Operation(summary = "Afficher toutes les motos", description = "Affiche toutes les motos du parc.")
    @ApiResponse(responseCode = "201", description = "Affiche toutes les motos avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    List<MotoResponseDto> afficherToutesMotos() {
        return motoService.listerMoto();
    }

    @PostMapping
    @Operation(summary = "Ajouter une moto", description = "Ajoute une moto dans le parc.")
    @ApiResponse(responseCode = "201", description = "Moto ajoutée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> ajouterMoto(@RequestBody MotoRequestDto motoRequestDto) {
        motoService.ajouterMoto(motoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une moto", description = "Supprime une moto dans le parc.")
    @ApiResponse(responseCode = "201", description = "Moto supprimée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<Void> supprimerMoto(@PathVariable("id") int id) {
        motoService.retirerDuParc(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une moto", description = "Permet de modifier une moto dans le parc.")
    @ApiResponse(responseCode = "201", description = "Moto modifiée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<MotoResponseDto> modifierMoto(@PathVariable("id") int id, @RequestBody @Valid MotoRequestDto motoRequestDto) {
        MotoResponseDto reponse = motoService.modifierMoto(id, motoRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/filtre")
    @Operation(summary = "Filtrage des motos", description = "Filtre les motos présentes dans le parc :" + " actif, inactif, dans le parc et hors du parc ")
    @ApiResponse(responseCode = "201", description = "Motos filtrées avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    ResponseEntity<List<MotoResponseDto>> filtrer(@RequestParam Filtre filtre) {
        List<MotoResponseDto> motos = motoService.filtrer(filtre);
        return ResponseEntity.ok(motos);
    }

}