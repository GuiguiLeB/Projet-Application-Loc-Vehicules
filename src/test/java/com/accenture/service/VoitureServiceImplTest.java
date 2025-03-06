package com.accenture.service;



import com.accenture.exception.ClientException;
import com.accenture.exception.VoitureException;
import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.Transmission;
import com.accenture.model.TypeVoiture;
import com.accenture.repository.VoitureDao;
import com.accenture.service.dto.AdresseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.mapper.VoitureMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {

    @Mock
    VoitureDao daoMock;

    @Mock
    VoitureMapper mapperMock;

    @InjectMocks
    VoitureServiceImpl service;



    @DisplayName("""
            Test de la méthode ajouterVoiture (int id) qui doit renvoyer une exception lorsque
             la méthode renvoie un (null)
            """)

    @Test
    void testAjouter() {
        assertThrows(ClientException.class, () -> service.ajouterVoiture(null));
    }

    @DisplayName("""
            Si ajouterVoiture(VoitureRequestDto avec marque null ) exception levée
            """)
    @Test
    void testAjouterSansMarque() {
        VoitureRequestDto voiture = new VoitureRequestDto(null, "ya",
                "rouge", 3, Carburant.ESSENCE,5, Transmission.AUTO,true,2,
                TypeVoiture.VOITURE_ELECTRIQUE,Permis.A2,150,15000,true,true);
        VoitureException ve = assertThrows(VoitureException.class, () -> service.ajouterVoiture(voiture));
        System.out.println(ve.getMessage());
    }

    @DisplayName("""
            Si ajouterVoiture(VoitureRequestDto avec marque blank ) exception levée
            """)
    @Test
    void testAjouterMarqueBlank() {
        VoitureRequestDto voiture = new VoitureRequestDto("\t", "ya",
                "rouge", 3, Carburant.ESSENCE,5, Transmission.AUTO,true,2,
                TypeVoiture.VOITURE_ELECTRIQUE,Permis.A2,150,15000,true,true);
        VoitureException ve = assertThrows(VoitureException.class, () -> service.ajouterVoiture(voiture));
        System.out.println(ve.getMessage());
    }











}