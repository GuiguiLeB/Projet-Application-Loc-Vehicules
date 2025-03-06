package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.MotoException;
import com.accenture.exception.VoitureException;
import com.accenture.model.Filtre;
import com.accenture.model.Permis;
import com.accenture.repository.MotoDao;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.mapper.MotoMapper;
import com.accenture.service.mapper.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MotoServiceImpl implements MotoService {

    public static final String ID_NON_PRESENT = "id non présent";
    private final MotoDao motoDao;
    private final MotoMapper motoMapper;

    public MotoServiceImpl(MotoDao motoDao, MotoMapper motoMapper) {
        this.motoDao = motoDao;
        this.motoMapper = motoMapper;
    }

    /**
     * Trouve une moto par son identifiant unique et renvoie une représentation DTO de la moto.
     *
     * @param id l'identifiant unique de la moto à rechercher
     * @return un objet {@link MotoResponseDto} contenant les informations de la moto trouvée
     * @throws MotoException en cas d'erreur spécifique à la gestion des motos
     * @throws EntityNotFoundException si aucune moto correspondant à l'identifiant donné n'est trouvée
     */
    public MotoResponseDto trouverMoto(int id) throws MotoException {
        Optional<Moto> optMoto = motoDao.findById(id);
        if (optMoto.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto moto = optMoto.get();
        return motoMapper.toMotoResponseDto(moto);
    }


    /**
     * Récupère les informations d'une moto par son identifiant, avec la possibilité de filtrer
     * selon son état d'activation.
     *
     * @param id l'identifiant unique de la moto
     * @param actif si vrai, retourne toutes les motos, sinon uniquement les motos actives
     * @return un objet {@link MotoResponseDto} contenant les informations de la moto trouvée
     * @throws EntityNotFoundException si aucune moto correspondant à l'identifiant ou aux critères n'est trouvée
     */
    public MotoResponseDto recupererInfosMoto(int id, boolean actif) {
        Moto moto = motoDao.findById(id)
                .filter(m -> actif || m.isActif())
                .orElseThrow(() -> new EntityNotFoundException("Id invalide ou moto indisponible"));
        return motoMapper.toMotoResponseDto(moto);
    }



    /**
     * Ajoute une nouvelle moto à la base de données après vérification des données fournies.
     * Transforme le DTO de requête en entité, applique les validations nécessaires,
     * enregistre l'entité et retourne une représentation DTO de la moto enregistrée.
     *
     * @param motoRequestDto un objet {@link MotoRequestDto} contenant les informations de la moto à ajouter
     * @return un objet {@link MotoResponseDto} représentant la moto ajoutée après enregistrement
     * @throws MotoException si les validations échouent pour la moto fournie
     */
    @Override
    public MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto) throws MotoException {
        Moto moto = motoMapper.toMoto(motoRequestDto);
        verifierMoto(moto);

        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);
    }

    /**
     * Récupère la liste de toutes les motos disponibles dans la base de données
     * et les transforme en objets DTO pour être retournées.
     *
     * @return une liste d'objets {@link MotoResponseDto} représentant toutes les motos
     *         enregistrées dans la base de données
     */

    @Override
    public List<MotoResponseDto> listerMoto() {
        return motoDao
                .findAll()
                .stream()
                .map(motoMapper::toMotoResponseDto)
                .toList();
    }


    /**
     * Modifie les informations d'une moto existante en utilisant son identifiant et les données fournies.
     * Vérifie si la moto existe dans la base de données, applique les validations nécessaires,
     * met à jour l'entité et enregistre les changements.
     *
     * @param id l'identifiant unique de la moto à modifier
     * @param motoRequestDto un objet {@link MotoRequestDto} contenant les nouvelles informations de la moto
     * @return un objet {@link MotoResponseDto} représentant la moto modifiée après enregistrement
     * @throws MotoException si les validations échouent pour les données de la moto
     * @throws EntityNotFoundException si aucune moto correspondant à l'identifiant fourni n'est trouvée
     */
    @Override
    public MotoResponseDto modifierMoto(int id, MotoRequestDto motoRequestDto) throws MotoException, EntityNotFoundException {
        if (!motoDao.existsById(id))
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Moto moto = motoMapper.toMoto(motoRequestDto);
        verifierMoto(moto);

        moto.setId(id);
        Moto registredMoto = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(registredMoto);
    }


    /**
     * Retire une moto du parc en fonction de son identifiant unique.
     * Si la moto est active, elle est marquée comme retirée du parc, sinon elle est supprimée de la base de données.
     *
     * @param id l'identifiant unique de la moto à retirer
     * @throws EntityNotFoundException si aucune moto correspondant à l'identifiant fourni n'est trouvée
     */
    public MotoResponseDto retirerDuParc(int id) throws EntityNotFoundException {
        // Vérifie si le véhicule existe dans la base de données
        Moto moto = motoDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("L'ID du véhicule n'est pas présent"));

        // Supprime le véhicule de la base de données
        motoDao.deleteById(id);
        return motoMapper.toMotoResponseDto(moto);
    }


    /**
     * Filtre la liste des motos en fonction du critère spécifié par le paramètre {@link Filtre}.
     * Les critères disponibles incluent :
     * - ACTIF : retourne uniquement les motos actives.
     * - INACTIF : retourne uniquement les motos inactives.
     * - HORSPARC : retourne les motos retirées du parc.
     * - DANSLEPARC : retourne les motos présentes dans le parc.
     *
     * @param filtre un objet {@link Filtre} indiquant le critère de filtrage (ACTIF, INACTIF, HORSPARC, DANSLEPARC)
     * @return une liste d'objets {@link MotoResponseDto} correspondant aux motos qui répondent au critère de filtrage
     * @throws IllegalArgumentException si le filtre fourni n'est pas reconnu ou pris en charge
     */
    @Override
    public List<MotoResponseDto> filtrer(Filtre filtre){
        List<Moto> listeMotos = motoDao.findAll();
        return switch (filtre){
            case ACTIF ->
                    listeMotos.stream()
                            .filter(Moto::isActif)
                            .map(motoMapper::toMotoResponseDto)
                            .toList();
            case INACTIF ->
                    listeMotos.stream()
                            .filter(moto -> !moto.isActif())
                            .map(motoMapper::toMotoResponseDto)
                            .toList();
            case HORSPARC ->
                    listeMotos.stream()
                            .filter(Moto::isRetireDuParc)
                            .map(motoMapper::toMotoResponseDto)
                            .toList();
            case DANSLEPARC ->
                    listeMotos.stream()
                            .filter(moto -> !moto.isRetireDuParc())
                            .map(motoMapper::toMotoResponseDto)
                            .toList();
            default ->
                    throw new IllegalArgumentException("Le filtre n'est pas disponible " + filtre);
        };
    }


    /************************************************************
     METHODE PRIVEE
     *************************************************************/

    private void verifierMoto (Moto moto) throws MotoException {
        if (moto == null)
            throw new MotoException("Moto est nul");
        if (moto.getMarque() == null || moto.getMarque().isBlank())
            throw new MotoException("La marque de la moto est absente");
        if (moto.getModele() == null || moto.getModele().isBlank())
            throw new MotoException("Le modèle de la moto est absent");
        if (moto.getCouleur() == null || moto.getCouleur().isBlank())
            throw new MotoException("La couleur de la moto est absente");
        if (moto.getCylindree() <= 0)
            throw new MotoException("La cylindrée est absente");
        if (moto.getTransmission() == null)
            throw new MotoException("Le mode de transmission est absent");
        if (moto.getNbCylindres() <= 0)
            throw new ClientException("Le nombre de cylindres est absent");
        if (moto.getPoids() <= 0)
            throw new ClientException("Le poids est absent");
        if (moto.getPuissanceKw() <= 0)
            throw new ClientException("La puissance de la moto est absente");
        if (moto.getHauteurSelle() <= 0)
            throw new ClientException("La hauteur de selle est absente");
        if (moto.getTypeMoto() == null)
            throw new MotoException("Veuillez choisir le type de moto svp");

        // on ajoute une ternaire
        moto.setPermis(
                (moto.getCylindree() <= 125 && moto.getPuissanceKw() <= 11) ? Permis.A1 :
                        (moto.getPuissanceKw() <= 35) ? Permis.A2 : Permis.A
        );

    }


}
