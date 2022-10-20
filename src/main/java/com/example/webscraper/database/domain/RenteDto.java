package com.example.webscraper.database.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RenteDto {
    private String risicoklasse;
    private String renteVasteDuur;
    private String rente;
    private String renteWijzigingsDatum;
}
