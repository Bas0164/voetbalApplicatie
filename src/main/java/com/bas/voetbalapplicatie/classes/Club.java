package com.bas.voetbalapplicatie.classes;

import javafx.scene.image.Image;

import java.sql.Blob;

public class Club {
    private int id; // ID van de club
    private String clubNaam; // Naam van de club
    private Blob logo; // Logo van de club opgeslagen als een Blob (Binary Large Object)
    private String sNaam; // Naam van het stadion waar de club speelt

    // Constructor voor het initialiseren van Club-objecten
    public Club(int id, String clubNaam, Blob logo, String sNaam) {
        this.id = id;
        this.clubNaam = clubNaam;
        this.logo = logo;
        this.sNaam = sNaam;
    }

    // Getter-methode om het ID van de club op te halen
    public int getId() {
        return id;
    }

    // Setter-methode om het ID van de club in te stellen
    public void setId(int id) {
        this.id = id;
    }

    // Getter-methode om de naam van de club op te halen
    public String getClubNaam() {
        return clubNaam;
    }

    // Setter-methode om de naam van de club in te stellen
    public void setClubNaam(String clubNaam) {
        this.clubNaam = clubNaam;
    }

    // Getter-methode om het logo van de club op te halen
    public Blob getLogo() {
        return logo;
    }

    // Setter-methode om het logo van de club in te stellen
    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    // Getter-methode om de naam van het stadion op te halen
    public String getStadion() {
        return sNaam;
    }

    // Setter-methode om de naam van het stadion in te stellen
    public void setStadion(String stadion) {
        this.sNaam = stadion;
    }
}
