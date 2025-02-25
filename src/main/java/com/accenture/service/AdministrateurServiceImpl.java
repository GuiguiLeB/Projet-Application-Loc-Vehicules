package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.entity.Administrateur;
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
     *
     * @param
     * @return
     * @throws AdministrateurException
     */
    @Override
    public AdminResponseDto trouverAdmin(String email) throws AdministrateurException {
        Optional<Administrateur> optAdmin = administrateurDao.findById(email);
        if (optAdmin.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Administrateur administrateur = optAdmin.get();
        return administrateurMapper.toAdminResponseDto(administrateur);

    }


    @Override
    public List<AdminResponseDto> listeAdmin() {
        return administrateurDao
                .findAll()
                .stream()
                .map(administrateurMapper::toAdminResponseDto)
                .toList();
    }



    @Override
    public AdminResponseDto ajouterAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException {

        Administrateur administrateur = administrateurMapper.toAdministrateur(adminRequestDto);

        Administrateur backedAdmin = administrateurDao.save(administrateur);
        return administrateurMapper.toAdminResponseDto(backedAdmin);
    }

    @Override
    public AdminResponseDto modifierAdmin(String email, AdminRequestDto adminRequestDto) throws AdministrateurException, EntityNotFoundException {
        if (!administrateurDao.existsById(email))
            throw new EntityNotFoundException(ID_NON_PRESENT);
        verifierAdmin(adminRequestDto);
        Administrateur administrateur = administrateurMapper.toAdministrateur(adminRequestDto);
        administrateur.setEmail(email);
        Administrateur enregAdmin = administrateurDao.save(administrateur);
        return administrateurMapper.toAdminResponseDto(enregAdmin);
    }



    public void supprimerAdmin(String email) throws EntityNotFoundException {
        if (administrateurDao.existsById(email))
            administrateurDao.deleteById(email);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
    }


    public void verifierAdmin(AdminRequestDto adminRequestDto) throws AdministrateurException {
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
