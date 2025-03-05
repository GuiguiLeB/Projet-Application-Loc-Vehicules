package com.accenture.service.mapper;

import com.accenture.repository.entity.vehicules.Moto;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface MotoMapper {
    Moto toMoto(MotoRequestDto motoRequestDto);
    MotoResponseDto toMotoResponseDto(Moto moto);
}
