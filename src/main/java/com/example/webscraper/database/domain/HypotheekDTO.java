package com.example.webscraper.database.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HypotheekDTO {
    public String hypotheeknummer;
    private Double hypotheeksom;
    private LocalDate ingangsdatum;
    private Set<LeningdeelDto> leningdelen = new HashSet<>();

}
