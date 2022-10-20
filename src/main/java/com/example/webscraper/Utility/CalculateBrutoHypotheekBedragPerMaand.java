package com.example.webscraper.Utility;

public class CalculateBrutoHypotheekBedragPerMaand {

    private final static int AANTAL_MAANDEN_PER_JAAR = 12;

    public Double calculateBrutoHypotheekBedragPerLeningDeel(String hypotheekrente, Double leningbedrag) {

         return (leningbedrag * Double.parseDouble(hypotheekrente)) / AANTAL_MAANDEN_PER_JAAR ;
    }
}
