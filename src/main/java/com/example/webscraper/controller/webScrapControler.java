package com.example.webscraper.controller;

import com.example.webscraper.database.domain.Rente;
import com.example.webscraper.database.domain.RenteDto;
import com.example.webscraper.database.repository.RenteRepository;
import com.example.webscraper.service.RenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class webScrapControler {

    public RenteService renteService;
    public RenteRepository renteRepository;

    @Autowired
    public webScrapControler(RenteService renteService, RenteRepository renteRepository) {
        this.renteService = renteService;
        this.renteRepository = renteRepository;
    }

    @GetMapping("/variabelerente")
    public Rente getVariableRente(@RequestParam String renteVasteDuur){
        return renteRepository.save(renteService.findHypotheekRente(renteVasteDuur));
    }

    @GetMapping("/rentes")
    public List<Rente> getRente() {
        List<Rente> renteList = renteRepository.findAll();
        return renteList;
    }

    @PostMapping("/rente/save")
    public Rente saveRente(@RequestBody RenteDto renteDto) {
        System.out.println(renteDto);
        Rente rente = new Rente(
                renteDto.getRisicoklasse(),
                renteDto.getRenteVasteDuur(),
                Double.valueOf(renteDto.getRente()),
                LocalDate.parse(renteDto.getRenteWijzigingsDatum()));
        renteService.saveRente(rente);
        return rente;
    }

}

