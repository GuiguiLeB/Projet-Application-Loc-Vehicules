package com.accenture.controller.advice;


import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.AdministrateurService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {

    private final AdministrateurService administrateurService;

    public AdministrateurController(AdministrateurService administrateurService) {
        this.administrateurService = administrateurService;
    }

    @GetMapping("/{id}")
    ResponseEntity<AdminResponseDto> unAdmin(@PathVariable("id") String email){
        AdminResponseDto trouveAdm = administrateurService.trouverAdmin(email);
        return ResponseEntity.ok(trouveAdm);
    }
    @GetMapping
    List<AdminResponseDto> tous(){
        return administrateurService.listeAdmin();
    }


    @PostMapping
    ResponseEntity<Void> ajouterAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        administrateurService.ajouterAdmin(adminRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprAdmin(@PathVariable("id") String email) {
        administrateurService.supprimerAdmin(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<AdminResponseDto>modifierAdmin(@PathVariable("id")String email, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto reponse = administrateurService.modifierAdmin(email, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }


}
