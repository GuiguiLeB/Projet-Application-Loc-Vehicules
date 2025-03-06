package com.accenture.advice;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.exception.MotoException;
import com.accenture.exception.VoitureException;
import com.accenture.model.ErreurReponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(ClientException ex ){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(),"Erreur fonctionnelle liée au client",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);


    } @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurReponse> entityNotFoundException(EntityNotFoundException ex ){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(),"Mauvaise requête",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurReponse> problemeValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErreurReponse er = new ErreurReponse(LocalDateTime.now(), "Validation erreur", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(AdministrateurException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(AdministrateurException ex ){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(),"Erreur fonctionnelle liée à l'administrateur",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);

    }
    @ExceptionHandler(VoitureException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(VoitureException ex ){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(),"Erreur fonctionnelle liée à l'administrateur",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);

    }
    @ExceptionHandler(MotoException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(MotoException ex ){
        ErreurReponse er = new ErreurReponse(LocalDateTime.now(),"Erreur fonctionnelle liée à l'administrateur",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);

    }




}
