package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VoitureServiceImpl implements VoitureService {

public static final String ID_NON_PRESENT = "id non présent";
private final VoitureDao voitureDao;
private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    /**
     * Trouve une voiture par son identifiant unique et renvoie une représentation DTO de la voiture.
     *
     * @param id l'identifiant unique de la voiture à rechercher
     * @return un objet {@link VoitureResponseDto} représentant la voiture trouvée
     * @throws VoitureException si une erreur spécifique liée à la gestion des voitures survient
     * @throws EntityNotFoundException si aucune voiture avec l'identifiant donné n'est trouvée
     */
    public VoitureResponseDto trouverVoiture(int id) throws VoitureException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseDto(voiture);

    }


    /**
     * Récupère les informations d'une voiture par son identifiant, avec une possibilité
     * de filtrer en fonction de son état d'activation.
     *
     * @param id l'identifiant unique de la voiture à rechercher
     * @param actif si vrai, retourne toutes les voitures, sinon uniquement les voitures actives
     * @return un objet {@link VoitureResponseDto} contenant les informations de la voiture trouvée
     * @throws EntityNotFoundException si aucune voiture correspondant à l'identifiant ou aux critères n'est trouvée
     */
    public VoitureResponseDto recupererInfosVoiture (int id, boolean actif) {
//        Optional<Voiture> optVoiture = voitureDao.findById(id);
//        Voiture voiture = optVoiture.orElseThrow(() -> new EntityNotFoundException("Id invalide"));
//        if (!actif && !voiture.isActif())
//            throw new EntityNotFoundException("voiture indisponible");
        Voiture voiture = voitureDao.findById(id)
                .filter(v -> actif || v.isActif())
                .orElseThrow(() -> new EntityNotFoundException("Id invalide ou voiture indisponible"));
        return voitureMapper.toVoitureResponseDto(voiture);
    }


    /**
     * Ajoute une nouvelle voiture à la base de données en transformant le DTO de requête en entité
     * et en sauvegardant l'entité persistante. Retourne un DTO de réponse avec les informations de la voiture enregistrée.
     *
     * @param voitureRequestDto un objet {@link VoitureRequestDto} contenant les informations de la voiture à ajouter
     * @return un objet {@link VoitureResponseDto} représentant la voiture ajoutée après son enregistrement
     * @throws VoitureException si une erreur de validation ou de traitement des données survient
     */

    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException {

    verifierVoiture(voitureRequestDto);
    Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
    Voiture voitureEnreg = voitureDao.save(voiture);
    return voitureMapper.toVoitureResponseDto(voitureEnreg);
}


    /**
     * p<Récupère la liste de toutes les voitures disponibles dans la base de données
     * et les transforme en objets DTO pour être retournés.>p
     *
     * @return une liste d'objets représentant toutes les voitures
     *         enregistrées dans la base de données
     */
    @Override
    public List<VoitureResponseDto> listerVoiture() {
        return voitureDao
                .findAll()
                .stream()
                .map(voitureMapper::toVoitureResponseDto)
                .toList();
    }

    /**
     * Modifie les informations d'une voiture existante en utilisant son identifiant et les données fournies.
     * Vérifie la présence de l'entité dans la base de données, applique les validations nécessaires,
     * et met à jour la voiture.
     *
     * @param id l'identifiant unique de la voiture à modifier
     * @param voitureRequestDto un objet {@link VoitureRequestDto} contenant les nouvelles informations de la voiture
     * @return un objet {@link VoitureResponseDto} représentant la voiture modifiée après enregistrement
     * @throws VoitureException si les validations échouent pour les données fournies
     * @throws EntityNotFoundException si aucune voiture correspondant à l'identifiant fourni n'existe
     */


    @Override
    public VoitureResponseDto modifierPartiellement(int id, VoitureRequestDto voitureRequestDto) throws VoitureException , EntityNotFoundException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);

        Voiture voitureExistante = optVoiture.get();

        Voiture nouvelle = voitureMapper.toVoiture(voitureRequestDto);

        remplacer(nouvelle, voitureExistante);

        Voiture voitureEnreg = voitureDao.save(voitureExistante);
        return voitureMapper.toVoitureResponseDto(voitureEnreg);


    }



    /**
     * Supprime une voiture du parc en utilisant son id.
     * La méthode vérifie si la voiture existe dans la base de données avant de la supprimer
     * et retourne une représentation DTO de la voiture supprimée.
     *
     * @param id l'identifiant unique de la voiture à supprimer
     * @return un objet {@link VoitureResponseDto} contenant les informations de la voiture supprimée
     * @throws EntityNotFoundException si aucune voiture correspondant à l'identifiant donné n'est trouvée
     */
    public VoitureResponseDto retirerDuParc(int id) throws EntityNotFoundException {
        // Vérifie si le véhicule existe dans la base de données
        Voiture voiture = voitureDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("L'ID du véhicule n'est pas présent"));

        // Supprime le véhicule de la base de données
        voitureDao.deleteById(id);
        return voitureMapper.toVoitureResponseDto(voiture);
    }





    /**
     * Filtre la liste des voitures en fonction du critère spécifié par le paramètre {@link Filtre}.
     * Le filtrage s'applique aux voitures actives, inactives, retirées du parc ou présentes dans le parc.
     *
     * @param filtre un objet {@link Filtre} indiquant le critère de filtrage (ex. ACTIF, INACTIF, HORSPARC, DANSLEPARC)
     * @return une liste d'objets {@link VoitureResponseDto} correspondant au critère de filtrage
     * @throws IllegalArgumentException si le type de filtre fourni n'est pas pris en charge
     */
    @Override
    public List<VoitureResponseDto> filtrer(Filtre filtre){
        List<Voiture> listeVoitures = voitureDao.findAll();
        List<VoitureResponseDto> result =        switch (filtre){
            case ACTIF ->
                    listeVoitures.stream()
                            .filter(Voiture::isActif)
                            .map(voitureMapper::toVoitureResponseDto)
                            .toList();            case INACTIF ->
                    listeVoitures.stream()
                            .filter(voiture -> !voiture.isActif() )
                            .map(voitureMapper::toVoitureResponseDto)
                            .toList();            case HORSPARC ->
                    listeVoitures.stream()
                            .filter(Voiture::isRetireDuParc)
                            .map(voitureMapper::toVoitureResponseDto)
                            .toList();            case DANSLEPARC ->
                    listeVoitures.stream()
                            .filter(voiture -> !voiture.isRetireDuParc())
                            .map(voitureMapper::toVoitureResponseDto)
                            .toList();            default ->
                    throw new IllegalArgumentException("Le filtre n'est pas disponible " + filtre);
        };    return result;}






    /************************************************************
     METHODES PRIVEES
     *************************************************************/


private static void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException {
    if (voitureRequestDto == null)
        throw new VoitureException("VoitureRequestDto est nul");
    if (voitureRequestDto.marque() == null || voitureRequestDto.marque().isBlank())
        throw new VoitureException("La marque de la voiture est absente");
    if (voitureRequestDto.modele() == null || voitureRequestDto.modele().isBlank())
        throw new VoitureException("Le modèle de la voiture est absent");
    if (voitureRequestDto.couleur() == null || voitureRequestDto.couleur().isBlank())
        throw new VoitureException("La couleur de la voiture est absente");
    if (voitureRequestDto.nbPlaces() <= 2)
        throw new VoitureException("Le nombre de places doit être supérieur ou égal à 2");
    if (voitureRequestDto.typeCarburant() == null)
        throw new VoitureException("Le type de carburant est absent");
    if (voitureRequestDto.nbPortes() != 3 && voitureRequestDto.nbPortes() != 5)
        throw new VoitureException("Le nombre de places doit être de 3 ou 5");
    if (voitureRequestDto.transmission() == null)
        throw new VoitureException("Le mode de transmission est absent");
    if (voitureRequestDto.nbBagages() == null || voitureRequestDto.nbBagages() <= 0)
        throw new ClientException("Le nombre de bagages ne peut pas être inférieur à 0");
    if (voitureRequestDto.typeVoiture() == null)
        throw new VoitureException("Veuillez choisir le type de voiture svp");
    if (voitureRequestDto.nbPlaces() > 9 && voitureRequestDto.nbPlaces() <= 16) {
        throw new VoitureException("Le permis requis est D1 pour ce nombre de places");
    } else if (voitureRequestDto.nbPlaces() <= 9 && voitureRequestDto.nbPlaces() >= 2) {
        // Permis B valide
    } else {
        throw new IllegalArgumentException("Nombre de places invalide pour un permis de conduire");
    }

}

    private static void remplacer(Voiture voiture, Voiture voitureExistante) {
        if (voiture.getMarque() != null)
            voitureExistante.setMarque(voiture.getMarque());
        if (voiture.getModele() != null)
            voitureExistante.setModele(voiture.getModele());
        if (voiture.getCouleur() != null)
            voitureExistante.setCouleur(voiture.getCouleur());
        if (voiture.getTypeCarburant() != null)
            voitureExistante.setTypeCarburant(voiture.getTypeCarburant());
        if (voiture.getTransmission() != null)
            voitureExistante.setTransmission(voiture.getTransmission());
        if (voiture.getTypeVoiture() != null)
            voitureExistante.setTypeVoiture(voiture.getTypeVoiture());


    }
}

