package com.accenture.controller.advice;

import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.ClientService;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    private final VoitureService voitureService;


    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseDto> uneVoiture(@PathVariable("id")int id){

        VoitureResponseDto trouve = voitureService.trouverVoiture(id);
        return ResponseEntity.ok(trouve);
    }
    @GetMapping
    List<VoitureResponseDto> toutesVoitures(){
        return voitureService.listeVoiture();
    }


    @PostMapping
    ResponseEntity<Void> ajouterVoiture(@RequestBody VoitureRequestDto voitureRequestDto) {
        voitureService.ajouterVoiture(voitureRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprimerVoiture(@PathVariable("id") int id) {
        voitureService.retirerDuParc(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}")
    ResponseEntity<VoitureResponseDto>modifierVoiture(@PathVariable("id")int id, @RequestBody @Valid VoitureRequestDto voitureRequestDto) {
        VoitureResponseDto reponse = voitureService.modifierVoiture(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/infos")
    public ResponseEntity<VoitureResponseDto> recupInfos(@RequestParam int id, @RequestParam boolean actif) {
        // Appeler le service pour récupérer les informations de la voiture
        VoitureResponseDto voitureInfo = voitureService.recupInfosVoiture(id, actif);

        // Vérifier si la voiture existe
        if (voitureInfo != null) {
            // Retourner les informations de la voiture avec un statut 200 (OK)
            return ResponseEntity.ok(voitureInfo);
        } else {
            // Si la voiture n'est pas trouvée, retourner un statut 404 (Non trouvé)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
