package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdresseMapper;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    private final String REGEX_MAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    private final String REGEX_PW = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$";
    public static final String ID_NON_PRESENT = "id non présent";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final AdresseMapper adresseMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, AdresseMapper adresseMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.adresseMapper = adresseMapper;
    }




    /**
     * Trouve un client par son email et retourne ses informations sous forme de DTO.
     * La méthode vérifie si un client avec l'email fourni existe dans la base de données.
     * En cas d'absence, une exception est levée.
     *
     * @param email l'email unique identifiant le client
     * @param password le mot de passe associé au client (paramètre non utilisé dans cette implémentation actuelle)
     * @return un objet {@link ClientResponseDto} contenant les informations du client trouvé
     * @throws ClientException si une erreur liée au client survient
     * @throws EntityNotFoundException si aucun client correspondant à l'email fourni n'est trouvé
     */
    public ClientResponseDto trouverClient(String email, String password) throws ClientException {
        Optional<Client> optClient = clientDao.findByEmailAndPassword(email,password);
        if (optClient.isEmpty())
            throw new EntityNotFoundException("l'adresse mail ou le mot de passe est incorrect");
        Client client = optClient.get();
        return clientMapper.toClientResponseDto(client);

    }


    /**
     * Récupère la liste de tous les clients dans la base de données
     * et transforme les entités client en objets DTO.
     *
     * @return une liste d'objets {@link ClientResponseDto} représentant tous les clients enregistrés
     */
    @Override
    public List<ClientResponseDto> listerClients() {
        return clientDao
                .findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    /**
     * Ajoute un nouveau client à la base de données après validation des données fournies.
     * Transforme le DTO de requête en entité, applique les validations nécessaires,
     * enregistre l'entité et retourne une représentation DTO du client enregistré.
     *
     * @param clientRequestDto un objet {@link ClientRequestDto} contenant les informations du client à ajouter
     * @return un objet {@link ClientResponseDto} représentant le client ajouté après enregistrement
     * @throws ClientException si les validations échouent pour les données fournies
     */
    @Override
    public ClientResponseDto ajouterClient(ClientRequestDto clientRequestDto) throws ClientException {
      verifierClient(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }



    /**
     * Modifie les informations d'un client existant en utilisant son email, son mot de passe et les données fournies.
     * Vérifie si le client existe dans la base de données en fonction de l'email et du mot de passe.
     * Valide les nouvelles données, applique les modifications et enregistre les changements dans la base de données.
     *
     * @param email l'email unique identifiant le client à modifier
     * @param password le mot de passe associé au client
     * @param clientRequestDto un objet {@link ClientRequestDto} contenant les nouvelles informations du client
     * @return un objet {@link ClientResponseDto} représentant le client modifié après enregistrement
     * @throws ClientException si les validations échouent pour les données fournies
     * @throws EntityNotFoundException si aucun client correspondant à l'email et au mot de passe fournis n'est trouvé
     */
    @Override
    public ClientResponseDto modifierClient(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {

        clientDao.findByEmailAndPassword(email,password).orElseThrow(() -> new EntityNotFoundException(ID_NON_PRESENT));
       verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);
        Client registrdClient = clientDao.save(client);
        return clientMapper.toClientResponseDto(registrdClient);
    }


    /**
     * Supprime un client de la base de données en utilisant son email et son mot de passe pour l'authentification.
     * Vérifie si le client existe avant de procéder à sa suppression.
     *
     * @param email l'email unique identifiant le client à supprimer
     * @param password le mot de passe associé au client
     * @throws EntityNotFoundException si aucun client correspondant à l'email et au mot de passe fournis n'est trouvé
     */
    public void supprimerClient(String email,String password)
            throws EntityNotFoundException {
        Client client = clientDao.findByEmailAndPassword(email, password).orElseThrow(()->new EntityNotFoundException("utilisateur non trouvé"));
        clientDao.delete(client);
    }



    /************************************************************
     METHODE PRIVEE
     *************************************************************/


    private void verifierClient(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null)
            throw new ClientException("ClientRequestDto est nul");
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank())
            throw new ClientException("Le nom du client est absent");
        if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank())
            throw new ClientException("Le prénom du client est absent");
        if (clientRequestDto.email() == null)
            throw new ClientException("L'adresse email du client est absente");
        if (!clientRequestDto.email().matches(REGEX_MAIL))
            throw new ClientException("L'adresse email du client n'est pas au bon format");
        if (clientRequestDto.dateNaissance() == null)
            throw new ClientException("La date de naissance est absente");
        if (!ageRequis(clientRequestDto.dateNaissance()))
            throw new ClientException("Le client doit avoir plus de 18 ans");
        if (clientRequestDto.password() == null)
            throw new ClientException("Le mot de passe est absent");
        if (!clientRequestDto.password().matches(REGEX_PW))
            throw new ClientException("Le mot de passe n'est pas au bon format");
        if (clientRequestDto.adresse().ville() == null || clientRequestDto.adresse().ville().isBlank())
            throw new ClientException("Le nom de la ville est absent");
        if (clientRequestDto.adresse().rue() == null || clientRequestDto.adresse().rue().isBlank())
            throw new ClientException("Le nom de la rue est absent");
        if (clientRequestDto.adresse().codePostal() == null || clientRequestDto.adresse().codePostal().isBlank())
            throw new ClientException("Le code postal est absent");
        if (clientRequestDto.permis() == null || clientRequestDto.permis().isEmpty())
            throw new ClientException("Le permis est obligatoire");
    }



    private static boolean ageRequis(LocalDate dateNaissance) {
        if (dateNaissance == null) { return false; }
        return Period.between(dateNaissance, LocalDate.now()).getYears() >= 18;
    }
}



