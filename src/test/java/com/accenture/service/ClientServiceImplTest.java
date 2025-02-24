package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;


    @DisplayName("""
            Test de la méthode ajouterClient (String email) qui doit renvoyer une exception lorque
             la méthode renvoie un (null)
            """)

    @Test
    void testAjouter() {
        assertThrows(ClientException.class, () -> service.ajouterClient(null));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec nom null ) exception levée
            """)
    @Test
    void testAjouterSansNom(){
        ClientRequestDto client = new ClientRequestDto(null,"lolo", new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec nom blank ) exception levée
            """)
    @Test
    void testAjouterNomBlank(){
        ClientRequestDto client = new ClientRequestDto("\t ","lolo", new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto avec prenom null ) exception levée
            """)
    @Test
    void testAjouterSansPrenom(){
        ClientRequestDto client = new ClientRequestDto("Gaby",null, new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec prenom blank ) exception levée
            """)
    @Test
    void testAjouterAvecPrenomBlank(){
        ClientRequestDto client = new ClientRequestDto("Gaby","\t ", new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec rue adresse null ) exception levée
            """)
    @Test
    void testAjouterSansRueAdresse(){
        ClientRequestDto client = new ClientRequestDto("Gaby","lolo",new AdresseDto(null,"44000","NANTES"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto avec code postal adresse null ) exception levée
            """)
    @Test
    void testAjouterSansCodePostalAdresse() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", null, "NANTES"), "gigi@hotmail.fr",
                "ok", LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec rue adresse null ) exception levée
            """)
    @Test
    void testAjouterSansVilleAdresse(){
        ClientRequestDto client = new ClientRequestDto("Gaby","lolo",new AdresseDto("pavé","44000",null),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(ClientRequestDto avec email null ) exception levée
            """)
    @Test
    void testAjouterSansMail(){
        ClientRequestDto client = new ClientRequestDto("Gaby","lolo",new AdresseDto("pavé","44000","NANTES"),null,
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(ClientRequestDto avec email null ) exception levée
            """)
    @Test
    void testAjouterAvecMailBlank(){
        ClientRequestDto client = new ClientRequestDto("Gaby","lolo",new AdresseDto("pavé","44000","NANTES"),"\t",
                "ok",LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, ()->service.ajouterClient(client));
    }





















    @DisplayName("""
            Si ajouter(TacheRequestDto avec adresse blank ) exception levée
            """)
    void testAjouterAvecAdresseBlank(){
        ClientRequestDto client = new ClientRequestDto("Gaby","/t ", new AdresseDto("\t","\t","\t"),"gigi@hotmail.fr",
                "ok",LocalDate.now(), List.of(Permis.A));
    }

















//    @Test
//    void testAjouterClient(){
//        ClientRequestDto requestDto = new ClientRequestDto("re",);
//        Tache tacheAvantEnreg = creerTachePromenade();
//        tacheAvantEnreg.setId(0);
//
//        Tache tacheApresEnreg = creerTachePromenade();
//        TacheResponseDto responseDto = creerTacheResponseDtoPromenade();
//
//        Mockito.when(mapperMock.toTache(requestDto)).thenReturn(tacheAvantEnreg);
//        Mockito.when(daoMock.save(tacheAvantEnreg)).thenReturn(tacheApresEnreg);
//        Mockito.when(mapperMock.toTacheResponseDto(tacheApresEnreg)).thenReturn(responseDto);
//
//        assertSame(responseDto, service.ajouter(requestDto));
//    }















































    @Test
    void testTrouverExistePas() {
        //simulation que la tache n'existe pas en base

        Mockito.when(daoMock.findById("")).thenReturn(Optional.empty());

        //vérifier que la méthode trouver (50) renvoie bien une exception
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(""));
        assertEquals("id non présent", ex.getMessage());

    }
//
//    @DisplayName("""
//            Test de la méthode trouver(int id) qui doit renvoyer une exception lorque
//             le client existe en base
//            """)
//
//    @Test
//    void testTrouverClientExistant() throws ClientException {
//        when(daoMock.findById(1)).thenReturn(Optional.of(client));
//        when(clientMapper.toClientResponseDto(client)).thenReturn(clientResponseDto);
//
//        ClientResponseDto result = clientService.trouver(1);
//
//        assertNotNull(result);
//        assertEquals(clientResponseDto, result);
//    }
//
//    @Test
//    void testTrouverClientNonExistant() {
//        when(clientDao.findById(1)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
//            clientService.trouver(1);
//        });
//
//        assertEquals("ID_NON_PRESENT", exception.getMessage());
//    }
}
