package com.example.webscraper.database.repository;

import com.example.webscraper.database.domain.Rente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RenteRepository extends JpaRepository<Rente, Integer> {

    boolean existsRenteByRenteWijzigingsDatum(LocalDate date);

}
