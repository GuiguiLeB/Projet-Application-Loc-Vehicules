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

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    public static final String ID_NON_PRESENT = "id non présent";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final AdresseMapper adresseMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, AdresseMapper adresseMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.adresseMapper = adresseMapper;
    }

    public ClientResponseDto trouver(int id) throws ClientException {
        Optional<Client> optClient = clientDao.findById(id);
        if (optClient.isEmpty())
            throw new EntityNotFoundException(ID_NON_PRESENT);
        Client client = optClient.get();
        return clientMapper.toClientResponseDto(client);

    }

    @Override
    public List<ClientResponseDto> listeClients() {
        return clientDao
                .findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }

    @Override
    public ClientResponseDto ajouterClient(ClientRequestDto clientRequestDto) throws ClientException {
//        validateClient(clientRequestDto);

        Adresse adresse = adresseMapper.toAdresse(clientRequestDto.adresse());

        Client client = clientMapper.toClient(clientRequestDto);
        client.setAdresse(adresse);
        Client backedClient = clientDao.save(client);
        return clientMapper.toClientResponseDto(backedClient);    }



    public void supprimerClient(int id) throws EntityNotFoundException {
        if (clientDao.existsById(id))
            clientDao.deleteById(id);
        else
            throw new EntityNotFoundException(ID_NON_PRESENT);
    }

//    private static void clientVerify(ClientRequestDto clientRequestDto) throws ClientException {
//        if (clientRequestDto == null)
//            throw new ClientException("ClientRequestDto is null");
//        if (clientRequestDto.name() == null || clientRequestDto.name().isBlank())        throw new ClientException("Client's name is absent");    if (clientRequestDto.firstName() == null)        throw new ClientException("Client's first name is absent");    if (clientRequestDto.email() == null)        throw new ClientException("Client's email is absent");    if (clientRequestDto.birthDate() == null)        throw new ClientException("Client's birth date is absent");    if (clientRequestDto.password() == null)        throw new ClientException("Client's password is absent");    if (clientRequestDto.address().town() == null)        throw new ClientException("Client's address is absent");    if (clientRequestDto.address().street() == null)        throw new ClientException("Client's address is absent");    if (clientRequestDto.address().postalCode() == null)        throw new ClientException("Client's address is absent");    if (LocalDate.now().minus(Period.between(clientRequestDto.birthDate(), LocalDate.now())).getYear() < 18)        throw new ClientException("Client MUST be over 18 years old");}



//
//    private void validateClient(ClientRequestDto clientRequestDto) {
//        if (!isMajor(clientRequestDto.dateNaissance())) {
//            throw new ClientException("Le client doit être majeur pour s'inscrire.");
//        }
//        if (!isValidEmail(clientRequestDto.email())) {
//            throw new ClientException("L'email n'est pas valide.");
//        }
//        if (!isValidPassword(clientRequestDto.password())) {
//            throw new ClientException("Le mot de passe n'est pas valide.");
//        }

//    }

//    private boolean isMajor(LocalDate dateNaissance) {
//        LocalDate today = LocalDate.now();
//        LocalDate birthday = LocalDate.of(dateNaissance.getYear(), dateNaissance.getMonth() , dateNaissance.getDayOfYear());
//        Period age = Period.between(birthday, today);
//        return age.getYears() >= 18;
//    }

//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
//        return Pattern.compile(emailRegex).matcher(email).matches();
//    }

//    private boolean isValidPassword(String password) {
//        if (password.length() < 8 || password.length() > 16) {
//            return false;
//        }
//        if (!password.matches(".*[A-Z].*")) {
//            return false;
//        }
//        if (!password.matches(".*[a-z].*")) {
//            return false;
//        }
//        if (!password.matches(".*[0-9].*")) {
//            return false;
//        }
//        if (!password.matches(".*[&#@\\-_$§].*")) {
//            return false;
//        }
//        return true;
//    }



}
