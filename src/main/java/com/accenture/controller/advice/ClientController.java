package com.accenture.controller.advice;


import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDto> unClient(@PathVariable("id") int id){
        ClientResponseDto trouve = clientService.trouver(id);
        return ResponseEntity.ok(trouve);
    }
    @GetMapping
    List<ClientResponseDto>tous(){
        return clientService.listeClients();
    }


    @PostMapping
    ResponseEntity<Void> ajouterClient(@RequestBody ClientRequestDto clientRequestDto) {
      clientService.ajouterClient(clientRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> suppr(@PathVariable("id") int id) {
        clientService.supprimerClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
