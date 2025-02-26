package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VoitureException;
import com.accenture.repository.VoitureDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.ClientResponseDto;
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

    public VoitureResponseDto trouverVoiture(int id) throws VoitureException {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        if (optVoiture.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Voiture voiture = optVoiture.get();
        return voitureMapper.toVoitureResponseDto(voiture);

    }

    public VoitureResponseDto recupInfosVoiture (int id, boolean actif) {
        Optional<Voiture> optVoiture = voitureDao.findById(id);
        Voiture voiture = optVoiture.orElseThrow(() -> new EntityNotFoundException("Id invalide"));
        if (!actif && !voiture.isActif())
            throw new EntityNotFoundException("voiture indisponible");
        return voitureMapper.toVoitureResponseDto(voiture);}


    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException {

    verifierVoiture(voitureRequestDto);
    Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
    Voiture voitureEnreg = voitureDao.save(voiture);
    return voitureMapper.toVoitureResponseDto(voitureEnreg);
}

    @Override
    public List<VoitureResponseDto> listeVoiture() {
        return voitureDao
                .findAll()
                .stream()
                .map(voitureMapper::toVoitureResponseDto)
                .toList();
    }

    @Override
    public VoitureResponseDto modifierVoiture(int id, VoitureRequestDto voitureRequestDto) throws VoitureException, EntityNotFoundException {
        if (!voitureDao.existsById(id))
            throw new EntityNotFoundException(ID_NON_PRESENT);

        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);
        voiture.setId(id);
        Voiture registredVoiture = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseDto(registredVoiture);
    }


    public void retirerDuParc(int id) throws EntityNotFoundException {
        // Vérifie si le véhicule existe dans la base de données
        Voiture voiture = voitureDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException("L'ID du véhicule n'est pas présent"));
        // Vérifie si le véhicule a des locations associées
        if (voiture.isActif()) {
            // Met à jour le booléen 'retiré du parc'
            voiture.setRetireDuParc(true);
            voitureDao.save(voiture);
        } else {
            // Supprime le véhicule de la base de données
            voitureDao.deleteById(id);
        }
    }









public void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VoitureException {
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
        throw new ClientException("Le code postal est absent");
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
}

