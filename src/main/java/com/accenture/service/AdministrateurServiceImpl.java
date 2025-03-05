package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.mapper.AdministrateurMapper;
import com.accenture.service.mapper.AdresseMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    private final String REGEX_MAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    private final String REGEX_PW = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$";
    public static final String ID_NON_PRESENT = "id non présent";
    private final AdministrateurDao administrateurDao;
    private final AdministrateurMapper administrateurMapper;
    private final AdresseMapper adresseMapper;

    public AdministrateurServiceImpl(AdministrateurDao administrateurDao, AdministrateurMapper administrateurMapper, AdresseMapper adresseMapper) {
        this.administrateurDao = administrateurDao;
        this.administrateurMapper = administrateurMapper;
        this.adresseMapper = adresseMapper;
    }


    /**
     * Trouve un administrateur en utilisant son email et retourne ses informations sous forme de DTO.
     * La méthode vérifie si un administrateur avec l'email fourni existe dans la base de données.
     * En cas d'absence, une exception est levée.
     *
     * @param email l'email unique identifiant l'administrateur à rechercher
     * @param password le mot de passe associé à l'administrateur (paramètre actuellement non utilisé dans cette implémentation)
     * @return un objet {@link AdminResponseDto} contenant les informations de l'administrateur trouvé
     * @throws AdministrateurException si une erreur liée à la gestion des administrateurs survient
     * @throws EntityNotFoundException si aucun administrateur correspondant à l'email fourni n'est trouvé
     */
    @Override
    public AdminResponseDto trouverAdmin(String email, String password) throws AdministrateurException {
        Optional<Administrateur> optAdmin = administrateurDao.findById(email);
        if (optAdmin.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Administrateur administrateur = optAdmin.get();
        return administrateurMapper.toAdminResponseDto(administrateur);

    }


    /**
     * Récupère la liste de tous les administrateurs dans la base de données
     * et transforme les entités administrateur en objets DTO.
     *
     * @return une liste d'objets {@link AdminResponseDto} représentant tous les administrateurs enregistrés
     */
    @Override
    public List<AdminResponseDto> listerAdmin() {
        return administrateurDao
                .findAll()
                .stream()
                .map(administrateurMapper::toAdminResponseDto)
                .toList();
    }



    /**
     * Ajoute un nouvel administrateur dans la base de données après validation des données fournies.
     * Transforme le DTO de requête en entité, applique les validations nécessaires,
     * enregistre l'entité et retourne une représentation DTO de l'administrateur ajouté.
     *
     * @param adminRequestDto un objet {@link AdminRequestDto} contenant les informations de l'administrateur à ajouter
     * @return un objet {@link AdminResponseDto} représentant l'administrateur ajouté après enregistrement
     * @throws AdministrateurException si les validations échouent pour les données fournies
     */
    @Override
    public AdminResponseDto ajouterAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException {
        verifierAdmin(adminRequestDto);
        Administrateur administrateur = administrateurMapper.toAdministrateur(adminRequestDto);
        Administrateur backedAdmin = administrateurDao.save(administrateur);
        return administrateurMapper.toAdminResponseDto(backedAdmin);
    }

    /**
     * Modifie les informations d'un administrateur existant en utilisant son email, son mot de passe
     * et les données fournies. Vérifie si l'administrateur existe dans la base de données
     * en fonction de l'email et du mot de passe. Valide les nouvelles données, applique les
     * modifications et enregistre les changements dans la base de données.
     *
     * @param email l'email unique identifiant l'administrateur à modifier
     * @param password le mot de passe associé à l'administrateur
     * @param adminRequestDto un objet {@link AdminRequestDto} contenant les nouvelles informations
     *                         de l'administrateur
     * @return un objet {@link AdminResponseDto} représentant l'administrateur modifié après enregistrement
     * @throws AdministrateurException si les validations échouent pour les données fournies
     * @throws EntityNotFoundException si aucun administrateur correspondant à l'email et au mot de passe fournis n'est trouvé
     */

    @Override
    public AdminResponseDto modifierAdmin(String email,String password, AdminRequestDto adminRequestDto) throws AdministrateurException, EntityNotFoundException {
        administrateurDao.findByEmailAndPassword(email,password).orElseThrow(() -> new EntityNotFoundException(ID_NON_PRESENT));
//        if (!existingClient.getPassword().equals(password)) {
//            throw new ClientException("Mot de passe incorrect");
//        }
        verifierAdmin(adminRequestDto);
        Administrateur administrateur = administrateurMapper.toAdministrateur(adminRequestDto);
        administrateur.setEmail(email);
        Administrateur registredAdmin = administrateurDao.save(administrateur);
        return administrateurMapper.toAdminResponseDto(registredAdmin );
    }


    /**
     * Supprime un administrateur de la base de données en utilisant son email pour l'identifier.
     * Vérifie si l'administrateur existe avant de procéder à la suppression.
     *
     * @param email l'email unique identifiant l'administrateur à supprimer
     * @throws EntityNotFoundException si aucun administrateur correspondant à l'email fourni n'est trouvé
     */
    public void supprimerAdmin(String email) throws EntityNotFoundException {
        if (administrateurDao.existsById(email))
            administrateurDao.deleteById(email);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
    }



    /************************************************************
     METHODE PRIVEE
     *************************************************************/

    private void verifierAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException {
        if (adminRequestDto == null)
            throw new AdministrateurException("AdminRequestDto est nul");
        if (adminRequestDto.nom() == null || adminRequestDto.nom().isBlank())
            throw new AdministrateurException("Le nom de l'administrateur est absent");
        if (adminRequestDto.prenom() == null || adminRequestDto.prenom().isBlank())
            throw new AdministrateurException("Le prénom de l'administrateur est absent");
        if (adminRequestDto.email() == null)
            throw new AdministrateurException("L'adresse email de l'administrateur est absente");
        if (!adminRequestDto.email().matches(REGEX_MAIL))
            throw new AdministrateurException("L'adresse email de l'administrateur n'est pas au bon format");
        if (adminRequestDto.password() == null)
            throw new AdministrateurException("Le mot de passe est absent");
        if (!adminRequestDto.password().matches(REGEX_PW))
            throw new AdministrateurException("Le mot de passe n'est pas au bon format");
        if (adminRequestDto.fonction() == null)
            throw new AdministrateurException("La fonction admin est absente");

    }




}
