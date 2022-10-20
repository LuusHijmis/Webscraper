package com.example.webscraper.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="leningdeel")
@NoArgsConstructor
@Data
public class Leningdeel {
    @Id
    @GeneratedValue
    private Integer id;

        private String leningnummer;
        private String omschrijving;
        private Double leenbedrag;
        private Double oorspronkelijkeRente;
        private LocalDate ingangsdatumRenteWijziging;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="hypotheek_id", nullable=false)
    @JsonIgnore
    private HypotheekEntity hypotheek;
}
