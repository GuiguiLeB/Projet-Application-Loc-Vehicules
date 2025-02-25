package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdresseMapper;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @Mock
    AdresseMapper adresseMapper;

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
    void testAjouterSansNom() {
        ClientRequestDto client = new ClientRequestDto(null, "lolo", new AdresseDto("pavé", "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec nom blank ) exception levée
            """)
    @Test
    void testAjouterNomBlank() {
        ClientRequestDto client = new ClientRequestDto("\t ", "lolo", new AdresseDto("pavé", "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, () -> service.ajouterClient(client));
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto avec prenom null ) exception levée
            """)
    @Test
    void testAjouterSansPrenom() {
        ClientRequestDto client = new ClientRequestDto("Gaby", null, new AdresseDto("pavé", "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        assertThrows(ClientException.class, () -> service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec prenom blank ) exception levée
            """)
    @Test
    void testAjouterAvecPrenomBlank() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "\t ", new AdresseDto("pavé", "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec rue adresse null ) exception levée
            """)
    @Test
    void testAjouterSansRueAdresse() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto(null, "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec rue adresse blank ) exception levée
            """)
    @Test
    void testAjouterAvecRueAdresseBlank() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("\t", "44000", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto avec code postal adresse null ) exception levée
            """)
    @Test
    void testAjouterSansCodePostalAdresse() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", null, "NANTES"), "gigi@hotmail.fr",
                "AZErtyuiop34", LocalDate.of(2000, 12, 20), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec CP adresse blank ) exception levée
            """)
    @Test
    void testAjouterAvecCodePostalAdresseBlank() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "\t", "NANTES"), "gigi@hotmail.fr",
                "Azertuiop23", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());
    }


    @DisplayName("""
            Si ajouter(TacheRequestDto avec ville adresse null ) exception levée
            """)
    @Test
    void testAjouterSansVilleAdresse() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "44000", null), "gigi@hotmail.fr",
                "AZErtyuiop34", LocalDate.of(2000, 12, 20), List.of(Permis.A));
        assertThrows(ClientException.class, () -> service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(TacheRequestDto avec ville adresse null ) exception levée
            """)
    @Test
    void testAjouterAvecVilleAdresseBlank() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "44000", "\t"), "gigi@hotmail.fr",
                "AZErtyuiop34", LocalDate.of(2000, 12, 20), List.of(Permis.A));
        assertThrows(ClientException.class, () -> service.ajouterClient(client));
    }

    @DisplayName("""
            Si ajouter(ClientRequestDto avec email null ) exception levée
            """)
    @Test
    void testAjouterSansMail() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "44000", "NANTES"), null,
                "AZErtyuiop34", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());

    }

    @DisplayName("""
            Si ajouter(ClientRequestDto avec email blank ) exception levée
            """)
    @Test
    void testAjouterAvecMailBlank() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "44000", "NANTES"), "\t",
                "AZErtyuiop34", LocalDate.now(), List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());

    }

//    @DisplayName("""
//            Si ajouter(ClientRequestDto avec age inférieur à 18 ) exception levée
//            """)
//    @Test
//    void testAjouterAvecAgeInferieur18(){
//        ClientRequestDto client = new ClientRequestDto("Gaby","lolo",new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
//                "AZErtyuiop34",LocalDate.of(2000,12,12), List.of(Permis.A));
//        ClientException ce = assertThrows( ClientException.class, ()->service.ajouterClient(client));
//        assertEquals("Le client doit avoir plus de 18 ans",ce.getMessage());
//
//    }

    @DisplayName("""
            Si ajouter(ClientRequestDto avec dateNaissance null ) exception levée
            """)
    @Test
    void testAjouterAvecDateNaissanceNull() {
        ClientRequestDto client = new ClientRequestDto("Gaby", "lolo", new AdresseDto("pavé", "44000", "NANTES"), "gigi@hotmail.fr",
                "AZErtyuiop34", null, List.of(Permis.A));
        ClientException ce = assertThrows(ClientException.class, () -> service.ajouterClient(client));
        System.out.println(ce.getMessage());

    }


    @DisplayName("""
            Test de la méthode trouver(string email) qui doit renvoyer une exception lorsque
             la client existe en base
            """)


    @Test
    void testTrouverExiste() {
        //simulation que la tache existe en base
        Client c = creerClient1();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findById("gigi@hotmail.fr")).thenReturn(optClient);
        ClientResponseDto dto = creerClientResponseDtoClient1();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);

        assertSame(dto, service.trouver("gigi@hotmail.fr"));


    }


    private static Client creerClient1() {
        Client c = new Client();
        c.setNom("Gaby");
        c.setPrenom("lolo");
        c.setAdresse(new Adresse(1, "pavé", "44000", "NANTES"));
        c.setPassword("AZErtyuiop34");
        c.setEmail("gigi@hotmail.fr");
        c.setDateNaissance(LocalDate.of(2000, 12, 15));
        c.setDateInscription(LocalDate.of(2022, 12, 24));
        c.setPermis(List.of(Permis.C));
        return c;
    }

    private static Client creerClient2() {
        Client c = new Client();
        c.setNom("RIRI");
        c.setPrenom("lulu");
        c.setAdresse(new Adresse(2, "route", "33200", "Bordeaux"));
        c.setPassword("AZErtyuiop35");
        c.setEmail("gigo@hotmail.fr");
        c.setDateNaissance(LocalDate.of(2001, 7, 10));
        c.setDateInscription(LocalDate.of(2023, 12, 24));
        c.setPermis(List.of(Permis.D));
        return c;
    }

    private static ClientResponseDto creerClientResponseDtoClient1() {
        return new ClientResponseDto(
                "Gaby", "lolo",
                new Adresse(1, "pavé", "44000", "NANTES"), "gigi@hotmail.fr", LocalDate.of(2000, 12, 15),
                LocalDate.of(2022, 12, 24), List.of(Permis.C)
        );
    }

    private static ClientResponseDto creerClientResponseDtoClient2() {
        return new ClientResponseDto(
                "RIRI", "lulu",
                new Adresse(2, "route", "33200", "Bordeaux"), "gigo@hotmail.fr", LocalDate.of(2001, 7, 10),
                LocalDate.of(2023, 12, 24), List.of(Permis.A)
        );
    }


    @DisplayName("""
            Test de la méthode listeClients(int id) qui doit renvoyer une liste de TacheResponseDto
           correspondant aux clients 
             existants en base
            """)

    @Test
    void testlisteClients(){
        Client client1 = creerClient1();
        Client client2 = creerClient2();


        List<Client> clients = List.of(client1, client2);
        ClientResponseDto clientResponseDtoClient1 = creerClientResponseDtoClient1();
        ClientResponseDto clientResponseDtoClient2 = creerClientResponseDtoClient2();
        List<ClientResponseDto> dtos = List.of(creerClientResponseDtoClient1(),creerClientResponseDtoClient2());

        Mockito.when(daoMock.findAll()).thenReturn(clients);
        Mockito.when(mapperMock.toClientResponseDto(client1)).thenReturn(clientResponseDtoClient1);
        Mockito.when(mapperMock.toClientResponseDto(client2)).thenReturn(clientResponseDtoClient2);

        assertEquals(dtos, service.listeClients());
    }

    @Test
    void testAjouterOK(){
        ClientRequestDto requestDto = new ClientRequestDto(
                "Gaby", "lolo",
                new AdresseDto("rue", "44000" , "NANTES"), "gigi@hotmail.fr","AZErtyuiop34", LocalDate.of(2000, 12, 15),
               List.of(Permis.C)) ;
        Client clientAvantEnreg = creerClient1();
        clientAvantEnreg.setEmail("gigi@gmail.com");

        Client clientApresEnreg = creerClient1();
        ClientResponseDto responseDto = creerClientResponseDtoClient1();

        Mockito.when(adresseMapper.toAdresse(requestDto.adresse())).thenReturn(new Adresse());
        Mockito.when(mapperMock.toClient(requestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(responseDto);

        assertSame(responseDto, service.ajouterClient(requestDto));
    }

    @DisplayName(""" 
            Test de la methode supprimer qui doit supprimer un client
             """)
    @Test
    void testSupprimerExiste(){
        Client client = creerClient1();
        String email = client.getEmail();
        String password = client.getPassword();
        Mockito.when(daoMock.findByEmailAndPassword(email, password)).thenReturn(Optional.of(client));
        service.supprimerClient(email, password);
        Mockito.verify(daoMock, Mockito.times(1)).delete(client);
    }


    @DisplayName(""" 
            Test de la methode supprimer qui doit supprimer un client
             """)
    @Test
    void testSupprimerExistePas(){
        Mockito.when(daoMock.findByEmailAndPassword("tp@gmail.fr", "s")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimerClient("tp@gmail.fr", "s"));
        assertEquals("utilisateur non trouvé", ex.getMessage());
    }







//    @Test
//    void testModifier() {
//        Mockito.when(daoMock.existsById("gigi@gmail.com")).thenReturn(false);
//        ClientRequestDto requestDto = new ClientRequestDto("Gaby", "lolo",
//                new AdresseDto("rue", "44000" , "NANTES"), "gigi@hotmail.fr","AZErtyuiop34", LocalDate.of(2000, 12, 15),
//                List.of(Permis.C)) ;
//        ClientException ce = assertThrows(ClientException.class, () -> service.modifierClient("gigi@gmail.com","AZErtyuiop34",  ClientRequestDto);
//        System.out.println(ce.getMessage());
//    }



















































//
//    @Test
//    void testAjouterClient(){
//        ClientRequestDto requestDto = new ClientRequestDto("Gaby","lolo",new AdresseDto("pavé","44000","NANTES"),"gigi@hotmail.fr",
//                "AZErtyuiop34",null, List.of(Permis.A));
//        Client clientAvantEnreg = creerTachePromenade();
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
