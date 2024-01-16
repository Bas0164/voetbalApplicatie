package com.bas.voetbalapplicatie.classes;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection conn;

    public Database(){
        try {
            // Maak verbinding met de Database d.m.v. een url, user en wachtwoord
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie" , "root" , "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //---------------------------Clubs-----------------------------------//

    // Methode om het stadion-ID op te halen op basis van de naam
    private int getStadionID(String stadionNaam) {
        String query = "SELECT stadionID FROM stadion WHERE stadionNaam = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            preparedStatement.setString(1, stadionNaam);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("stadionID");
            }
            throw new RuntimeException("Stadion niet gevonden in de database!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Club> laatClubsZien() {
        // SQL-query om alle kolommen uit de 'club'-tabel te selecteren
        String query = "SELECT club.*, stadion.stadionNaam FROM club INNER JOIN stadion ON club.stadion = stadion.stadionID;";

        // ArrayList om Club-objecten op te slaan die uit de database zijn opgehaald
        ArrayList<Club> lijstMetClubs = new ArrayList<>();

        try {
            // Maak een Statement-object aan voor het uitvoeren van SQL-query's
            Statement stm = this.conn.createStatement();

            // Voer de SQL-query uit en verkrijg het resultaat
            stm.execute(query);
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {
                // Haal waarden op uit het resultaat voor elke club
                int id = rs.getInt("clubID");
                String sclubNaam =  rs.getString("clubNaam");
                Blob blogo =  rs.getBlob("logo");
                String istadion = rs.getString("stadion.stadionNaam");

                // Maak een nieuw Club-object aan met de opgehaalde waarden
                Club club = new Club(id, sclubNaam, blogo, istadion);

                // Voeg het Club-object toe aan de ArrayList
                lijstMetClubs.add(club);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Retourneer de ArrayList met Club-objecten
        return lijstMetClubs;
    }

    public void opslaanClub(String clubNaam, String logo, String stadionNaam) {
        // SQL-query om een nieuwe club toe te voegen met verwijzing naar het stadion via een subquery
        String query = "INSERT INTO club (clubnaam, logo, stadion) VALUES (?, ?, (SELECT stadionID FROM stadion WHERE stadionNaam = ?))";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Parameters invullen voor de PreparedStatement
            preparedStatement.setString(1, clubNaam);    // Clubnaam
            preparedStatement.setString(2, logo);        // Logo
            preparedStatement.setString(3, stadionNaam); // Stadionnaam

            // De query uitvoeren
            preparedStatement.executeUpdate();
            System.out.println("Club succesvol opgeslagen in de database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bewerkClub(Club c) {

        // Query met behulp van een prepared statement
        String query = "UPDATE club SET clubNaam = ?, logo = ?, stadion = ? WHERE clubID = ?";

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            // Parameters instellen voor het prepared statement
            preparedStatement.setString(1, c.getClubNaam());
            preparedStatement.setBlob(2, c.getLogo());
            preparedStatement.setInt(3, getStadionID(c.getStadion()));
            preparedStatement.setInt(4, c.getId());

            // De update uitvoeren
            preparedStatement.executeUpdate();

            System.out.println("Club succesvol bijgewerkt in de database!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void verwijderClub(Club c){

        // Query schrijven
        String query = "DELETE FROM club WHERE clubID = " + c.getId();

        try {
            // Statement-object aanmaken
            Statement stm = this.conn.createStatement();
            // Query uitvoeren
            stm.execute(query);
            System.out.println("Club succesvol verwijderd uit de database!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //---------------------------Spelers-----------------------------------//

    public ArrayList<Speler> laatSpelersZien() {
        // SQL-query om alle kolommen uit de 'speler'-tabel te selecteren
        String query = "SELECT speler.*, nationaliteit.nationaliteit, club.clubNaam, positie.positie FROM speler INNER JOIN nationaliteit ON speler.nationaliteit = nationaliteit.nationaliteitID INNER JOIN club ON speler.club = club.clubID INNER JOIN positie ON speler.positie = positie.positieID;";

        // ArrayList om Speler-objecten op te slaan die uit de database zijn opgehaald
        ArrayList<Speler> lijstMetSpelers = new ArrayList<>();

        try {
            // Maak een Statement-object aan voor het uitvoeren van SQL-query's
            Statement stm = this.conn.createStatement();

            // Voer de SQL-query uit en verkrijg het resultaat
            stm.execute(query);
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {
                // Haal waarden op uit het resultaat voor elke speler
                int id = rs.getInt("spelerID");
                String spelerNaam =  rs.getString("spelerNaam");
                int rugnummer = rs.getInt("rugnummer");
                String inationaliteit =  rs.getString("nationaliteit.nationaliteit");
                int aantalGoals = rs.getInt("aantalGoals");
                int aantalAssists = rs.getInt("aantalAssists");
                Blob profielfoto =  rs.getBlob("profielfoto");
                String iclub = rs.getString("club.clubNaam");
                String ipositie = rs.getString("positie.positie");

                // Maak een nieuw Speler-object aan met de opgehaalde waarden
                Speler speler = new Speler(id, spelerNaam, rugnummer, inationaliteit, aantalGoals, aantalAssists, profielfoto, iclub, ipositie);

                // Voeg het Speler-object toe aan de ArrayList
                lijstMetSpelers.add(speler);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Retourneer de ArrayList met Speler-objecten
        return lijstMetSpelers;
    }

    public void opslaanSpeler(String spelerNaam, Double rugnummer, String nationaliteit, Double aantalGoals, Double aantalAssists, String profielfoto, String club, String positie) {
        // SQL-query om een nieuwe speler toe te voegen met verwijzing naar nationaliteit, club en positie via een subquerys
        String query = "INSERT INTO speler (spelerNaam, rugnummer, nationaliteit, aantalGoals, aantalAssists, profielfoto, club, positie) VALUES (?, ?, (SELECT nationaliteitID FROM nationaliteit WHERE nationaliteit = ?), ?, ?, ?, (SELECT clubID FROM club WHERE clubNaam = ?), (SELECT positieID FROM positie WHERE positie = ?))";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Parameters invullen voor de PreparedStatement
            preparedStatement.setString(1, spelerNaam); // spelerNaam
            preparedStatement.setDouble(2, rugnummer); // rugnummer
            preparedStatement.setString(3, nationaliteit); // nationaliteit
            preparedStatement.setDouble(4, aantalGoals); // aantalGoals
            preparedStatement.setDouble(5, aantalAssists); // aantalAssists
            preparedStatement.setString(6, profielfoto); // profielfoto
            preparedStatement.setString(7, club); // club
            preparedStatement.setString(8, positie); // positie

            // De query uitvoeren
            preparedStatement.executeUpdate();
            System.out.println("Speler succesvol opgeslagen in de database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
