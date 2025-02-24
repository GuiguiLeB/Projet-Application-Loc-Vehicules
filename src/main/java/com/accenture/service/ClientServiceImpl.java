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
import java.util.regex.Pattern;


@Service
public class ClientServiceImpl implements ClientService {

    private static final String REGEX_MAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    private static final String REGEX_PW = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$";
    public static final String ID_NON_PRESENT = "id non présent";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final AdresseMapper adresseMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, AdresseMapper adresseMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.adresseMapper = adresseMapper;
    }

    public ClientResponseDto trouver(String email) throws ClientException {
        Optional<Client> optClient = clientDao.findById(email);
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
      verifierClient(clientRequestDto);

        Adresse adresse = adresseMapper.toAdresse(clientRequestDto.adresse());

        Client client = clientMapper.toClient(clientRequestDto);
        client.setAdresse(adresse);
        Client clientEnreg = clientDao.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);    }

//    @Overridepublic ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException {
//
//        clientVerify(clientRequestDto);
//        Client client = clientMapper.toClient(clientRequestDto);
//        Client backedClient = clientDao.save(client);
//        return clientMapper.toClientResponseDto(backedClient);}

    @Override
    public ClientResponseDto modifierClient(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        if (!clientDao.existsById(email))
            throw new EntityNotFoundException(ID_NON_PRESENT);
       verifierClient(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);
        Client registrdClient = clientDao.save(client);
        return clientMapper.toClientResponseDto(registrdClient);}


    public void supprimerClient(String email)
            throws EntityNotFoundException {
        if (clientDao.existsById(email))
            clientDao.deleteById(email);
    }



    public void verifierClient(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null)
            throw new ClientException("ClientRequestDto est nul");
        if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank())
            throw new ClientException("Le nom du client est absent");
        if (clientRequestDto.prenom() == null)
            throw new ClientException("Le prénom du client est absent");
        if (clientRequestDto.email() == null)
            throw new ClientException("L'adresse email du client est absente");
        if (!clientRequestDto.email().matches(REGEX_MAIL))
            throw new ClientException("L'adresse email du client n'est pas au bon format");
        if (clientRequestDto.dateNaissance() == null)
            throw new ClientException("La date de naissance est absente");
        if (clientRequestDto.password() == null)
            throw new ClientException("Le mot de passe est absent");
        if (!clientRequestDto.password().matches(REGEX_PW))
            throw new ClientException("Le mot de passe n'est pas au bon format");
        if (clientRequestDto.adresse().ville() == null)
            throw new ClientException("Le nom de la ville est absent");
        if (clientRequestDto.adresse().rue() == null)
            throw new ClientException("Le nom de la rue est absent");
        if (clientRequestDto.adresse().codePostal() == null)
            throw new ClientException("Le code postal est absent");
        if (clientRequestDto.permis()== null || clientRequestDto.permis().isEmpty())
            throw new ClientException("Le permis est obligatoire");
        if (LocalDate.now().minus(Period.between(clientRequestDto.dateNaissance(), LocalDate.now())).getYear() < 18)
            throw new ClientException("Le client doit avoir plus de 18 ans");}



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
//
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
//        return Pattern.compile(emailRegex).matcher(email).matches();
//    }
//
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
//


}
