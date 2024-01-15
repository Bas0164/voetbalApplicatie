package com.bas.voetbalapplicatie.classes;

import java.sql.Blob;

public class Speler {

    private int id;
    private String spelerNaam;
    private int rugnummer;
    private String nationaliteit;
    private int aantalGoals;
    private int aantalAssists;
    private Blob profielfoto;
    private String club;
    private String positie;


    public Speler(int id, String spelerNaam, int rugnummer, String nationaliteit, int aantalGoals, int aantalAssists, Blob profielfoto, String club, String positie) {
        this.id = id;
        this.spelerNaam = spelerNaam;
        this.rugnummer = rugnummer;
        this.nationaliteit = nationaliteit;
        this.aantalGoals = aantalGoals;
        this.aantalAssists = aantalAssists;
        this.profielfoto = profielfoto;
        this.club = club;
        this.positie = positie;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpelerNaam() {
        return spelerNaam;
    }

    public void setSpelerNaam(String spelerNaam) {
        this.spelerNaam = spelerNaam;
    }

    public int getRugnummer() {
        return rugnummer;
    }

    public void setRugnummer(int rugnummer) {
        this.rugnummer = rugnummer;
    }

    public int getAantalGoals() { return aantalGoals; }

    public void setAantalGoals(int aantalGoals) {
        this.aantalGoals = aantalGoals;
    }

    public int getAantalAssists() {
        return aantalAssists;
    }

    public void setAantalAssists(int aantalAssists) {
        this.aantalAssists = aantalAssists;
    }

    public Blob getProfielfoto() {
        return profielfoto;
    }

    public void setProfielfoto(Blob profielfoto) {
        this.profielfoto = profielfoto;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPositie() {
        return positie;
    }

    public void setPositie(String positie) {
        this.positie = positie;
    }

    public String getNationaliteit() { return nationaliteit; }

    public void setNationaliteit(String nationaliteit) { this.nationaliteit = nationaliteit; }

}
