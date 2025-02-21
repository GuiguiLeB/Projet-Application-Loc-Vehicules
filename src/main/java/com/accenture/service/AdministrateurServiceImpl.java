package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdministrateurMapper;
import com.accenture.service.mapper.AdresseMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {

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
     * @param id
     * @return
     * @throws AdministrateurException
     */
    @Override
    public AdminResponseDto trouverAdmin(int id) throws AdministrateurException {
        Optional<Administrateur> optAdmin = administrateurDao.findById(id);
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


    public void supprimerAdmin(int id) throws EntityNotFoundException {
        if (administrateurDao.existsById(id))
            administrateurDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
    }
}
