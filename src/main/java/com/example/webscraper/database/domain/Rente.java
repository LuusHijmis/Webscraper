package com.example.webscraper.database.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="rente")
@NoArgsConstructor
@Getter
public class Rente {

    @Id
    @GeneratedValue
    private Integer id;

    private String risicoklasse;
    private String renteVasteDuur;
    private Double rentePercentage;
    private LocalDate renteWijzigingsDatum;

    public Rente(String risicoklasse, String renteVasteDuur, Double rentePercentage, LocalDate renteWijzigingsDatum) {
        this.risicoklasse = risicoklasse;
        this.renteVasteDuur = renteVasteDuur;
        this.rentePercentage = rentePercentage;
        this.renteWijzigingsDatum = renteWijzigingsDatum;
    }
}
