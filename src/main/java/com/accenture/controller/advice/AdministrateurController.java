package com.accenture.controller.advice;


import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.AdministrateurService;
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
    ResponseEntity<AdminResponseDto> unAdmin(@PathVariable("id") int id){
        AdminResponseDto trouveAdm = administrateurService.trouverAdmin(id);
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
    ResponseEntity<Void> suppr(@PathVariable("id") int id) {
        administrateurService.supprimerAdmin(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
