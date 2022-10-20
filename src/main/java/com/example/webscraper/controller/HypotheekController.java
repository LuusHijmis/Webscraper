package com.example.webscraper.controller;

import com.example.webscraper.database.domain.*;
import com.example.webscraper.database.mapper.HypotheekMapper;
import com.example.webscraper.database.repository.HypotheekRepository;
import com.example.webscraper.database.repository.LeningdeelRepository;
import com.example.webscraper.service.BesparingService;
import com.example.webscraper.service.HypotheekService;
import com.example.webscraper.service.RenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class HypotheekController {


    private HypotheekMapper hypotheekMapper;
    private HypotheekRepository hypotheekRepository;
    private HypotheekService hypotheekService;
    private LeningdeelRepository leningdeelRepository;
    private BesparingService besparingService;

    @Autowired
    public HypotheekController(HypotheekMapper hypotheekMapper,
                               HypotheekRepository hypotheekRepository,
                               HypotheekService hypotheekService,
                               LeningdeelRepository leningdeelRepository,
                               BesparingService besparingService) {
        this.hypotheekMapper = hypotheekMapper;
        this.hypotheekRepository = hypotheekRepository;
        this.hypotheekService = hypotheekService;
        this.leningdeelRepository = leningdeelRepository;
        this.besparingService = besparingService;
    }

    @PostMapping("/hypotheekgegevens")
    public HypotheekDTO storeHypotheekGegevens(@RequestBody HypotheekDTO hypotheekDTO) {
        return hypotheekService.saveHypotheekgegevens(hypotheekDTO);
    }

    @PostMapping("/leningdeel")
    public HypotheekEntity storeLeningdeel(@RequestBody LeningdeelDto leningdeelDto) {
        System.out.println("controller: " + leningdeelDto);
        HypotheekEntity hypotheekEntityToShow = hypotheekService.saveLeningdelen(leningdeelDto);
        System.out.println("hypotheekToShow: " + hypotheekEntityToShow);
        return hypotheekEntityToShow;
    }

    @GetMapping("/hypotheek/{hypotheeknummer}")
    public HypotheekDTO getHypotheek(@PathVariable String hypotheeknummer) {
        return hypotheekMapper.EntitytoDto(hypotheekService.findHypotheek(hypotheeknummer));
    }

    @GetMapping("/besparing")
    public String getBesparing(@RequestParam String hypotheeknummer, @RequestParam String berekeningsdatum) {
        System.out.println("body: " + hypotheeknummer);
        Set<Leningdeel> leningdelen = hypotheekService.findHypotheek(hypotheeknummer).getLeningdelen();
        LocalDate date = LocalDate.parse(berekeningsdatum);
        Double besparing = besparingService.brutoBesparing(leningdelen, date);
        return LocalDate.now() + ": " + besparing.toString();
    }

    @GetMapping("/berekeningsoverzicht")
    public List<BerekeningsDetailsDTO> getBerekeningsoverzicht(@RequestParam String hypotheeknummer, @RequestParam String berekeningsdatum) {
        HypotheekEntity hypotheek = hypotheekService.findHypotheek(hypotheeknummer);
        return besparingService.getBerekeningsDetails(hypotheek, LocalDate.parse(berekeningsdatum));
    }

}
