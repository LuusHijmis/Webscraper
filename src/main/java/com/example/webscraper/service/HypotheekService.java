package com.example.webscraper.service;

import com.example.webscraper.database.domain.*;
import com.example.webscraper.database.mapper.HypotheekMapper;
import com.example.webscraper.database.mapper.LeningdeelMapper;
import com.example.webscraper.database.repository.HypotheekRepository;
import com.example.webscraper.database.repository.LeningdeelRepository;
import com.example.webscraper.database.repository.RenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HypotheekService {

    HypotheekRepository hypotheekRepository;
    HypotheekMapper hypotheekMapper;
    LeningdeelRepository leningdeelRepository;
    LeningdeelMapper leningdeelMapper;
    RenteRepository renteRepository;

    @Autowired
    public HypotheekService(HypotheekRepository hypotheekRepository, RenteRepository renteRepository, HypotheekMapper hypotheekMapper, LeningdeelRepository leningdeelRepository, LeningdeelMapper leningdeelMapper) {
        this.hypotheekRepository = hypotheekRepository;
        this.renteRepository = renteRepository;
        this.hypotheekMapper = hypotheekMapper;
        this.leningdeelRepository = leningdeelRepository;
        this.leningdeelMapper = leningdeelMapper;
    }

    public HypotheekDTO saveHypotheekgegevens(HypotheekDTO hypotheekDto) {
        hypotheekRepository.save(hypotheekMapper.dtoToEntity(hypotheekDto));
        HypotheekDTO hypotheekDTOToShow = hypotheekMapper.EntitytoDto(findHypotheekByHypotheeknummer());
        System.out.println(hypotheekDTOToShow);
        return hypotheekDTOToShow;
    }

    public HypotheekEntity saveLeningdelen(LeningdeelDto leningdeelDto) {
        HypotheekEntity hypotheekEntity = findHypotheekByHypotheeknummer();
        System.out.println("je moeder" + hypotheekEntity);
        if (hypotheekEntity != null) {
            Leningdeel leningdeel = leningdeelMapper.dtoToEntity(leningdeelDto);
            leningdeel.setHypotheek(hypotheekEntity);
            leningdeelRepository.save(leningdeel);
            return hypotheekEntity;
        }
        return null;
    }

    public HypotheekEntity findHypotheekByHypotheeknummer() {
        List<HypotheekEntity> hypotheken = new ArrayList<>();
        hypotheken.addAll(hypotheekRepository.findAll());
        System.out.println("findHypotheek: " + hypotheekMapper.EntitytoDto(hypotheken.get(0)));
        return hypotheken.get(0);
    }

    public HypotheekEntity findHypotheek(String hypotheeknummer) {
        return hypotheekRepository
                .findHypotheekEntityByHypotheeknummer(hypotheeknummer)
                .orElseThrow(NullPointerException::new);
    }


}
