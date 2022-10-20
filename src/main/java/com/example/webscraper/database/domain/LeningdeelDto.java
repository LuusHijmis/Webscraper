package com.example.webscraper.database.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeningdeelDto {

    private String Hypotheeknummer;
    private String leningnummer;
    private String omschrijving;
    private Double leenbedrag;
    private Double oorspronkelijkeRente;
    private LocalDate ingangsdatum;

}
