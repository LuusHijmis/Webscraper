package com.example.webscraper.database.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BerekeningsDetailsDTO {
    private String hypotheeknummer;
    private String leningsnummer;
    private Double leningbedrag;
    private Double oorspronkelijkeRente;
    private LocalDate rekendatum;
    private LocalDate rentewijzigingsdatum;
    private Double variabeleRente;
}
