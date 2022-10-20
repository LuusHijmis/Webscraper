package com.example.webscraper.database.repository;

import com.example.webscraper.database.domain.HypotheekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HypotheekRepository extends JpaRepository<HypotheekEntity, Integer> {

//    @Query("select hypotheek from HypotheekEntity hypotheek where hypotheek.hypotheeknummer = ?1")
    Optional<HypotheekEntity> findHypotheekEntityByHypotheeknummer(String hypotheeknummer);
}
