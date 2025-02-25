package com.accenture.controller.advice;


import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
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
    ResponseEntity<ClientResponseDto> unClient(@PathVariable("id") String email){

        ClientResponseDto trouve = clientService.trouver(email);
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
    ResponseEntity<Void> supprimerClient(@PathVariable("id") String email, String password) {
        clientService.supprimerClient(email,password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}")
    ResponseEntity<ClientResponseDto>modifierClient(@PathVariable("id")String email, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifierClient(email, clientRequestDto.password(), clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/recupInfos")
    public ResponseEntity<ClientResponseDto> recupInfos(@RequestParam String email, @RequestParam String password) {
        // Appeler le service pour récupérer les informations du client
        ClientResponseDto clientInfo = clientService.recupInfos(email, password);

        // Vérifier si le client existe
        if (clientInfo != null) {
            // Retourner les informations du client avec un statut 200 (OK)
            return ResponseEntity.ok(clientInfo);
        } else {
            // Si le client n'est pas trouvé, retourner un statut 404 (Non trouvé)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }





}
