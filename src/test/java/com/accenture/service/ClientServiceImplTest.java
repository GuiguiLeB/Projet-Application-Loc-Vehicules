package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl service;


    @DisplayName("""
            Test de la méthode trouver(int id) qui doit renvoyer une excpetion lorque
             le client n'existe pas en base
            """)

    @Test
    void testTrouverExistePas() {
        //simulation que la tache n'existe pas en base

        Mockito.when(daoMock.findById(50)).thenReturn(Optional.empty());

        //vérifier que la méthode trouver (50) renvoie bien une exception
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(50));
        assertEquals("id non présent", ex.getMessage());

    }
//
//    @DisplayName("""
//            Test de la méthode trouver(int id) qui doit renvoyer une exception lorque
//             le client existe en base
//            """)
//
//    @Test
//    void testTrouverClientExistant() throws ClientException {
//        when(daoMock.findById(1)).thenReturn(Optional.of(client));
//        when(clientMapper.toClientResponseDto(client)).thenReturn(clientResponseDto);
//
//        ClientResponseDto result = clientService.trouver(1);
//
//        assertNotNull(result);
//        assertEquals(clientResponseDto, result);
//    }
//
//    @Test
//    void testTrouverClientNonExistant() {
//        when(clientDao.findById(1)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
//            clientService.trouver(1);
//        });
//
//        assertEquals("ID_NON_PRESENT", exception.getMessage());
//    }
}
