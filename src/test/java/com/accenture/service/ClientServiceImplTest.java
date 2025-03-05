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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
            Test de la méthode ajouterClient (String email) qui doit renvoyer une exception lorsque
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
        when(daoMock.findById("gigi@hotmail.fr")).thenReturn(optClient);
        ClientResponseDto dto = creerClientResponseDtoClient1();
        when(mapperMock.toClientResponseDto(c)).thenReturn(dto);

        assertSame(dto, service.trouverClient("gigi@hotmail.fr","AZErtyuiop34"));


    }

    @Test
    void testTrouverClientExistePas() {
        // Simulation que le client n'existe pas en base
        when(daoMock.findById("client@example.com")).thenReturn(Optional.empty());

        // Vérifier que l'exception EntityNotFoundException est levée
        assertThrows(EntityNotFoundException.class, () -> service.trouverClient("client@example.com","AZErtyuiop34"));
    }

//    @Test
//    void testTrouverExistePas() {
//        //simulation que la tache n'existe pas en base
//
//        Mockito.when(daoMock.findById("gigi@hotmail.fr")).thenReturn(Optional.empty());
//
//        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouverClient("gigi@hotmail.fr","AZErtyuiop34"));
//        assertEquals("email non présent", ex.getMessage());
//        System.out.println(ex.getMessage());
//
//    }


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
                new Adresse(1, "pavé", "44000", "NANTES"), "gigi@hotmail.fr","AZErtyuiop34", LocalDate.of(2000, 12, 15),
                LocalDate.of(2022, 12, 24), List.of(Permis.C)
        );
    }

    private static ClientResponseDto creerClientResponseDtoClient2() {
        return new ClientResponseDto(
                "RIRI", "lulu",
                new Adresse(2, "route", "33200", "Bordeaux"), "gigo@hotmail.fr","AZErtyuiop35", LocalDate.of(2001, 7, 10),
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

        when(daoMock.findAll()).thenReturn(clients);
        when(mapperMock.toClientResponseDto(client1)).thenReturn(clientResponseDtoClient1);
        when(mapperMock.toClientResponseDto(client2)).thenReturn(clientResponseDtoClient2);

        assertEquals(dtos, service.listerClients());
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

        when(mapperMock.toClient(requestDto)).thenReturn(clientAvantEnreg);
        when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(responseDto);

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
        when(daoMock.findByEmailAndPassword(email, password)).thenReturn(Optional.of(client));
        service.supprimerClient(email, password);
        Mockito.verify(daoMock, Mockito.times(1)).delete(client);
    }

    @DisplayName(""" 
            Test de la methode supprimer qui doit supprimer un client
             """)
    @Test
    void testSupprimerExistePas(){
        when(daoMock.findByEmailAndPassword("tp@gmail.fr", "s")).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimerClient("tp@gmail.fr", "s"));
        assertEquals("utilisateur non trouvé", ex.getMessage());
    }


//    @Test
//    public void testModifierClient_Success() throws EntityNotFoundException, ClientException {
//        // Initialisation des données
//        String email = "test@example.com";
//        String password = "password123";
//        ClientRequestDto requestDto = new ClientRequestDto("Gaby", "lolo",
//                new AdresseDto("rue", "44000" , "NANTES"), "gigi@hotmail.fr","AZErtyuiop34", LocalDate.of(2000, 12, 15),
//                List.of(Permis.C)) ;
//        requestDto.setNom("Test User");
//
//        Client existingClient = new Client();
//        existingClient.setEmail(email);
//        existingClient.setPassword(password);
//
//        Client updatedClient = new Client();
//        updatedClient.setName("Test User");
//        updatedClient.setEmail(email);
//
//        ClientResponseDto responseDto = new ClientResponseDto();
//        responseDto.setName("Test User");
//
//        // Configuration des mocks
//        when(clientDao.findByEmailAndPassword(email, password))
//                .thenReturn(Optional.of(existingClient));
//        when(clientMapper.toClient(requestDto)).thenReturn(updatedClient);
//        when(clientDao.save(updatedClient)).thenReturn(updatedClient);
//        when(clientMapper.toClientResponseDto(updatedClient)).thenReturn(responseDto);
//
//        // Appel de la méthode à tester
//        ClientResponseDto result = clientService.modifierClient(email, password, requestDto);
//
//        // Assertions
//        assertNotNull(result);
//        assertEquals("Test User", result.getName());
//        verify(clientDao, times(1)).findByEmailAndPassword(email, password);
//        verify(clientDao, times(1)).save(updatedClient);
//        verify(clientMapper, times(1)).toClientResponseDto(updatedClient);
//    }


//    @Test
//    void testModifierClientExiste() throws ClientException, EntityNotFoundException {
//        // Simulation du client existant
//        Client c = creerClient1();
//        Optional<Client> optClient = Optional.of(c);
//        when(daoMock.findByEmailAndPassword("client@example.com", "password123")).thenReturn(optClient);
//
//        // Simulation du mapping et de la sauvegarde
//        ClientRequestDto clientRequestDto = creerClientRequestDto();
//        Client client = new Client();
//        when(mapperMock.toClient(clientRequestDto)).thenReturn(client);
//
//        Client savedClient = creerClientSauvegarde();
//        when(daoMock.save(client)).thenReturn(savedClient);
//
//        ClientResponseDto clientResponseDto = creerClientResponseDto(savedClient);
//        when(mapperMock.toClientResponseDto(savedClient)).thenReturn(clientResponseDto);
//
//        // Appel de la méthode et vérification
//        ClientResponseDto result = service.modifierClient("client@example.com", "password123", clientRequestDto);
//        assertSame(clientResponseDto, result);
//    }

//    void testModifierClientPartiellementOk() {
//        // Given
//        Client clientExistant = creerClient1();
//        String email = clientExistant.getEmail();
//        String motDePasse = clientExistant.getPassword();
//        ClientRequestDto clientRequestDto = new ClientRequestDto
//        Client clientMisAJour = creerClient2();
//        clientMisAJour.setNom("NouveauNom");
//        ClientResponseDto responseDto = new ClientResponseDto
//        Mockito.when(daoMock.findByEmail(email)).thenReturn(Optional.of(clientExistant));
//        Mockito.when(daoMock.save(Mockito.any(Client.class))).thenReturn(clientExistant);
//        Mockito.when(mapperMock.toClientResponseDto(clientMisAJour)).thenReturn(responseDto);
//        // When
//        ClientResponseDto response = service.modifierClient(email, motDePasse, clientRequestDto);
//        // Then
//        assertNotNull(response);
//        assertEquals("NouveauNom", response.nom());
//        Mockito.verify(daoMock, Mockito.times(1)).save(clientExistant);}
//    }

}
