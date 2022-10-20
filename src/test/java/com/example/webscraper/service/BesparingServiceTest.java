package com.example.webscraper.service;

import com.example.webscraper.database.domain.Leningdeel;
import com.example.webscraper.database.domain.LeningdeelDto;
import com.example.webscraper.database.mapper.LeningdeelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BesparingServiceTest {

    BesparingService besparingServiceUnderTest;
    LeningdeelMapper leningdeelMapper;


//    @Test
//    public void brutoBesparing() {
//        Set<Leningdeel> leningdelen = new HashSet<>();
//        LocalDate localDate = LocalDate.of(2009,9,1);
//        LeningdeelDto leningdeelDto = new LeningdeelDto("12345678", "1", "aflossingsvrij", 100000.00, 5.85, localDate);
//        Leningdeel leningdeel = leningdeelMapper.dtoToEntity(leningdeelDto);
//        leningdelen.add(leningdeel);
//        Double besparingsresultaat = besparingServiceUnderTest.brutoBesparing(leningdelen,LocalDate.of(2022,9,14));
//        assertThat(besparingsresultaat).isEqualTo(1.00);
//    }

}