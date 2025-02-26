package com.accenture.service.mapper;


import com.accenture.model.Permis;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoitureMapper {


    Voiture toVoiture(VoitureRequestDto voitureRequestDto);
    VoitureResponseDto toVoitureResponseDto(Voiture voiture);


}
