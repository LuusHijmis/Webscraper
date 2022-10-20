package com.example.webscraper.database.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="hypotheek")
//@NamedQuery(name= "Hypotheek.findHypotheekByHypotheeknummer", query = "select h from Hypotheek h where h.hypotheeknummer = ?1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HypotheekEntity {

    @Id
    @GeneratedValue
    @Column(name="id", unique = true)
    private Integer id;
    @Column(name="hypotheeknummer", unique = true)
    private String hypotheeknummer;
    @Column(name="hypotheeksom")
    private Double hypotheeksom;
    @Column(name="ingangsdatum")
    private LocalDate ingangsdatum;
    @OneToMany(mappedBy = "hypotheek")
    private Set<Leningdeel> leningdelen = new HashSet<>();

}
