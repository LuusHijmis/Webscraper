package com.example.webscraper.service;

import com.example.webscraper.database.domain.HypotheekEntity;
import com.example.webscraper.database.domain.Leningdeel;
import com.example.webscraper.database.domain.Rente;
import com.example.webscraper.database.domain.BerekeningsDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BesparingService {

    RenteService renteService;
    HypotheekService hypotheekService;

    @Autowired
    public BesparingService(RenteService renteService, HypotheekService hypotheekService) {
        this.renteService = renteService;
        this.hypotheekService = hypotheekService;
    }

    public Double brutoBesparing(Set<Leningdeel> leningDelen, LocalDate berekeningsdatum) {
        Double totaleBesparing = 0.00;
        System.out.println("leningdelen: " + leningDelen.size());
        for(Leningdeel leningdeel: leningDelen) {
            System.out.println(leningdeel.getLeenbedrag());
            totaleBesparing += brutoBesparingPerLeningdeel(leningdeel, berekeningsdatum);
        }

        return totaleBesparing;
    }

    private Double brutoBesparingPerLeningdeel(Leningdeel leningdeel, LocalDate berekeningsdatum) {
        Double besparing= 0.00;
        List<Rente> variabeleRenteList = getVariableRentesStartingFromBerekeningsdatum(berekeningsdatum, leningdeel);
        System.out.println(variabeleRenteList.size());
        for (Rente rente: variabeleRenteList) {
            Double renteVerschil = leningdeel.getOorspronkelijkeRente() - rente.getRentePercentage();
            besparing += renteVerschil * leningdeel.getLeenbedrag() / 100 / 12;
        }
        System.out.println(besparing);
        return besparing;
    }

    private List<Rente> getVariableRentesStartingFromBerekeningsdatum(LocalDate berekeningsdatum, Leningdeel leningdeel) {
        List<Rente> renteList = renteService.getAllRentes();
        List<Rente> renteListStartingFromBerekeningsdatum = new ArrayList<>();
        Rente eersteRenteVoorRenteWijzigingsdatum = findRenteOnRenteWijzigingsdatum(leningdeel.getIngangsdatumRenteWijziging());
        for (Rente rente : renteList) {
            if(rente.getRenteWijzigingsDatum().isAfter(berekeningsdatum)) {
                System.out.println("Rente in rentelijst: " + rente.getRenteWijzigingsDatum());
                renteListStartingFromBerekeningsdatum.add(rente);
            }
        }
        renteListStartingFromBerekeningsdatum.add(eersteRenteVoorRenteWijzigingsdatum);
        System.out.println("Eerste rente: " + renteListStartingFromBerekeningsdatum.get(0));
        return renteListStartingFromBerekeningsdatum;
    }

    private Rente findRenteOnRenteWijzigingsdatum(LocalDate berekeningsdatum) {
        List<Rente> renteList = renteService.getAllRentes();
        List<Rente> renteListBeforeRentewijziging = new ArrayList<>();
        for (Rente rente : renteList) {
            if (rente.getRenteWijzigingsDatum().isBefore(berekeningsdatum) || rente.getRenteWijzigingsDatum().equals(berekeningsdatum)){
            renteListBeforeRentewijziging.add(rente);
            }
        }
        renteListBeforeRentewijziging.sort(Comparator.comparing(Rente::getRenteWijzigingsDatum));
        for(Rente rente: renteListBeforeRentewijziging) {
            System.out.println("Rentelijst before: " + rente.getRenteWijzigingsDatum() + " " + rente.getRentePercentage());
        }
        return renteListBeforeRentewijziging.get(renteListBeforeRentewijziging.size()-1);
    }


    public List<BerekeningsDetailsDTO> getBerekeningsDetails(HypotheekEntity hypotheek, LocalDate berekeningsdatum) {
        List<BerekeningsDetailsDTO> tempList = new ArrayList<>();
        Set<Leningdeel> leningdelen = hypotheek.getLeningdelen();

//      hypotheeknr,    leningnummer   Leningbedrag     oorspronkelijkeRente    rekendatum  rentewijzigingsdatum    variabele rente
//      123456789           1           100000               6.00                    01-09-2022  28-08-2022              1.55
//      123456789           2           100000               6.00                    01-09-2022  28-08-2022              1.55
//      123456789           1           100000               6.00                    01-09-2022  25-09-2022              2.35
////    123456789           2           100000               6.00                    01-09-2022  25-09-2022              2.35
//      per berekeningsdatum loop door de leningen heen en maak je een berekeningsregel aan
        List<Rente> renteList = getRenteListStartingFromBerekeningsdatum(berekeningsdatum);

        for (Rente rente: renteList) {
            for (Leningdeel leningdeel1 : leningdelen) {
                System.out.println("regel: " + rente.getRenteWijzigingsDatum() + " " + leningdeel1.getLeenbedrag());
                BerekeningsDetailsDTO berekeningsDetailsDTO = new BerekeningsDetailsDTO(
                        hypotheek.getHypotheeknummer(),
                        leningdeel1.getLeningnummer(),
                        leningdeel1.getLeenbedrag(),
                        leningdeel1.getOorspronkelijkeRente(),
                        berekeningsdatum,
                        rente.getRenteWijzigingsDatum(),
                        rente.getRentePercentage()
                );
                tempList.add(berekeningsDetailsDTO);
            }
        }
        return tempList;
    }

    private List<Rente> getRenteListStartingFromBerekeningsdatum(LocalDate berekeningsdatum){
        List<Rente> renteList = renteService.getAllRentes();
        List<Rente> renteListToReturn = new ArrayList<>();
        for (Rente rente: renteList) {
            if(rente.getRenteWijzigingsDatum().isAfter(berekeningsdatum)){
                System.out.println("add to rentelist: " + rente.getRentePercentage() + rente.getRenteWijzigingsDatum());
                renteListToReturn.add(rente);
            }
        }
        System.out.println("Rente op rentewijzigingsdatum" + " " + findRenteOnRenteWijzigingsdatum(berekeningsdatum));
        renteListToReturn.add(findRenteOnRenteWijzigingsdatum(berekeningsdatum));
        return renteListToReturn;
    }


}
