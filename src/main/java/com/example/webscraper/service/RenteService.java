package com.example.webscraper.service;

import com.example.webscraper.database.domain.Rente;
import com.example.webscraper.database.repository.RenteRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Service
@EnableScheduling
public class RenteService {
    private final static String URL = "https://www.asr.nl/hypotheek/hypotheekrente";
    private final static String SELECTOR_TABLE_ROW = "tbody";

    RenteRepository renteRepository;

    @Autowired
    public RenteService(RenteRepository renteRepository) {
        this.renteRepository = renteRepository;
    }

    public void saveRente(Rente rente) {
        if (!renteRepository.existsRenteByRenteWijzigingsDatum(rente.getRenteWijzigingsDatum())) {
           renteRepository.save(rente);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    private void saveRenteMonthly() {
        System.out.println("scheduled run");
        Rente huidigeRente = findHypotheekRente("1 maand");
        boolean renteExists = renteRepository.existsRenteByRenteWijzigingsDatum(huidigeRente.getRenteWijzigingsDatum());
        if(!renteExists) {
            renteRepository.save(huidigeRente);
            System.out.println("rente opgeslagen");
        }
    }


    public Rente findHypotheekRente(String renteVasteDuur) {
        String hypotheekrente = null;
        Elements renteTabel = getWebElements(SELECTOR_TABLE_ROW);
        for(Element row : renteTabel) {
            Elements tableRows = row.getElementsByTag("tr");
            for (int i = 0; i < tableRows.size(); i++) {
                if (tableRows.get(i).text().contains(renteVasteDuur)) {
                    hypotheekrente = stringToStringArray(tableRows.get(i).text())[2];
                }
            }
        }

        if(hypotheekrente != null) {
            return createRenteObject(hypotheekrente, renteVasteDuur);
        }
        return null;
    }

    private Rente createRenteObject(String hypotheekrente, String renteVasteDuur) {
        String rente1 = hypotheekrente.replace(",",".");
        LocalDate renteWijzigingsDatum = LocalDate.parse(getRenteWijzigingsDatum());
        return new Rente("NHG",renteVasteDuur, Double.parseDouble(rente1), renteWijzigingsDatum);
    }

    public Elements getWebElements(String selector) {
        Connection conn = Jsoup.connect(URL);
        conn.userAgent("custom user agent");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Elements webElements = doc.select(selector);
        return webElements;
    }

    public String getRenteWijzigingsDatum() {
        Elements webelements = getWebElementsByClass();
        String dag = stringToStringArray(webelements.text())[3];
        if(dag.length() == 1) {
            dag = "0" + stringToStringArray(webelements.text())[3];
        }
        String maand = monthToStringNumber(stringToStringArray(webelements.text())[4]);
        System.out.println(maand);
        String jaar = stringToStringArray(webelements.text())[5];
        StringJoiner stringJoiner = new StringJoiner("-");
        stringJoiner.add(jaar).add(maand).add(dag);
        String date = stringJoiner.toString();
        return date;
    }

    private String monthToStringNumber(String month) {

        switch (month) {
            case "januari":
                return "01";
            case "februari":
                return "02";
            case "maart":
                return "03";
            case "april":
                return "04";
            case "mei":
                return "05";
            case "juni":
                return "06";
            case "juli":
                return "07";
            case "augustus":
                return "08";
            case "september":
                return "09";
            case "oktober":
                return "10";
            case "november":
                return "11";
            case "december":
                return "12";
            default:
                return "01";
        }
    }

    public Elements getWebElementsByClass() {
        Connection conn = Jsoup.connect(URL);
        conn.userAgent("custom user agent");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Elements webElements = doc.getElementsByClass("container--header");
        return webElements;
    }

    private String [] stringToStringArray(String string) {
        return string.split("\\s+");
    }

    public List<Rente> getAllRentes(){
        return renteRepository.findAll();
    }

}
