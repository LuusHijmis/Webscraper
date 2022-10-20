package com.example.webscraper.database.mapper;

import com.example.webscraper.database.domain.Leningdeel;
import com.example.webscraper.database.domain.LeningdeelDto;
import org.springframework.stereotype.Service;

@Service
public class LeningdeelMapper {

    public LeningdeelDto entityToDto(Leningdeel leningdeel) {
        LeningdeelDto leningdeelDto = new LeningdeelDto();
        leningdeelDto.setHypotheeknummer(leningdeel.getHypotheek().getHypotheeknummer());
        leningdeelDto.setLeningnummer(leningdeel.getLeningnummer());
        leningdeelDto.setIngangsdatum(leningdeel.getIngangsdatumRenteWijziging());
        leningdeelDto.setOorspronkelijkeRente(leningdeel.getOorspronkelijkeRente());
        leningdeelDto.setOmschrijving(leningdeel.getOmschrijving());
        leningdeelDto.setLeenbedrag(leningdeel.getLeenbedrag());
        return leningdeelDto;
    }

    public Leningdeel dtoToEntity(LeningdeelDto leningdeelDto) {
        System.out.println("dtoToEntity" + leningdeelDto);
        Leningdeel leningdeel = new Leningdeel();
        leningdeel.setLeningnummer(leningdeelDto.getLeningnummer());
        leningdeel.setIngangsdatumRenteWijziging(leningdeelDto.getIngangsdatum());
        leningdeel.setOorspronkelijkeRente(leningdeelDto.getOorspronkelijkeRente());
        leningdeel.setOmschrijving(leningdeelDto.getOmschrijving());
        leningdeel.setLeenbedrag(leningdeelDto.getLeenbedrag());
        return leningdeel;

    }
}
