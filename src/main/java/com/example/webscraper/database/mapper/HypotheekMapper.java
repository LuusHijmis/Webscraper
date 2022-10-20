package com.example.webscraper.database.mapper;

import com.example.webscraper.database.domain.HypotheekEntity;
import com.example.webscraper.database.domain.HypotheekDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class HypotheekMapper {

    private LeningdeelMapper leningdeelMapper;

    @Autowired
    public HypotheekMapper(LeningdeelMapper leningdeelMapper) {
        this.leningdeelMapper = leningdeelMapper;
    }

    public HypotheekEntity dtoToEntity(HypotheekDTO hypotheekDTO) {
        System.out.println(hypotheekDTO);
        HypotheekEntity hypotheekEntity = new HypotheekEntity();
        hypotheekEntity.setHypotheeknummer(hypotheekDTO.getHypotheeknummer());
        hypotheekEntity.setHypotheeksom(hypotheekDTO.getHypotheeksom());
        hypotheekEntity.setIngangsdatum(hypotheekDTO.getIngangsdatum());
        return hypotheekEntity;
    }
    public HypotheekDTO EntitytoDto(HypotheekEntity hypotheek) {
        HypotheekDTO hypotheekDto = new HypotheekDTO();
        if(hypotheek != null) {
            hypotheekDto.setHypotheeknummer(hypotheek.getHypotheeknummer());
            hypotheekDto.setHypotheeksom(hypotheek.getHypotheeksom());
            hypotheekDto.setIngangsdatum(hypotheek.getIngangsdatum());
            hypotheekDto.setLeningdelen(hypotheek.getLeningdelen().stream().map(leningdeelMapper::entityToDto).collect(Collectors.toSet()));

        }
        return hypotheekDto;
    }

}
