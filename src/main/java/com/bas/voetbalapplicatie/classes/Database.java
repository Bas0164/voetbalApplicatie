package com.bas.voetbalapplicatie.classes;

import java.io.FileInputStream;
import java.nio.file.Path;
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

    public void opslaanClub(String clubNaam, Path selectedFile, String stadionNaam) {
        // SQL-query om een nieuwe club toe te voegen met verwijzing naar het stadion via een subquery
        String query = "INSERT INTO club (clubnaam, logo, stadion) VALUES (?, ?, (SELECT stadionID FROM stadion WHERE stadionNaam = ?))";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Parameters invullen voor de PreparedStatement
            preparedStatement.setString(1, clubNaam); // Clubnaam
            FileInputStream fin = new FileInputStream(selectedFile.toString());
            preparedStatement.setBinaryStream(2, fin); // Logo
            preparedStatement.setString(3, stadionNaam); // Stadionnaam

            // De query uitvoeren
            preparedStatement.executeUpdate();
            System.out.println("Club succesvol opgeslagen in de database!");

        } catch (Exception e) {
            // Fouten afdrukken bij eventuele problemen
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

    // Methode om het club-ID op te halen op basis van de naam
    private int getNationaliteitID(String nationaliteit) {
        String query = "SELECT nationaliteitID FROM nationaliteit WHERE nationaliteit = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            preparedStatement.setString(1, nationaliteit);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("nationaliteitID");
            }
            throw new RuntimeException("Stadion niet gevonden in de database!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Methode om het club-ID op te halen op basis van de naam
    private int getClubID(String clubNaam) {
        String query = "SELECT clubID FROM club WHERE clubNaam = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            preparedStatement.setString(1, clubNaam);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("clubID");
            }
            throw new RuntimeException("Stadion niet gevonden in de database!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Methode om het positie-ID op te halen op basis van de naam
    private int getPositieID(String positie) {
        String query = "SELECT positieID FROM positie WHERE positie = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            preparedStatement.setString(1, positie);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("positieID");
            }
            throw new RuntimeException("Stadion niet gevonden in de database!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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

    public void opslaanSpeler(String spelerNaam, Integer rugnummer, String nationaliteit, Integer aantalGoals, Integer aantalAssists, Path selectedFile, String club, String positie) {
        // SQL-query om een nieuwe speler toe te voegen met verwijzing naar nationaliteit, club en positie via subqueries
        String query = "INSERT INTO speler (spelerNaam, rugnummer, nationaliteit, aantalGoals, aantalAssists, profielfoto, club, positie) VALUES (?, ?, (SELECT nationaliteitID FROM nationaliteit WHERE nationaliteit = ?), ?, ?, ?, (SELECT clubID FROM club WHERE clubNaam = ?), (SELECT positieID FROM positie WHERE positie = ?))";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Parameters invullen voor de PreparedStatement
            preparedStatement.setString(1, spelerNaam); // spelerNaam
            preparedStatement.setInt(2, rugnummer); // rugnummer
            preparedStatement.setString(3, nationaliteit); // nationaliteit
            preparedStatement.setInt(4, aantalGoals); // aantalGoals
            preparedStatement.setInt(5, aantalAssists); // aantalAssists

            // Profielfoto toevoegen als binair stream
            FileInputStream fin = new FileInputStream(selectedFile.toString());
            preparedStatement.setBinaryStream(6, fin); // Profielfoto

            preparedStatement.setString(7, club); // club
            preparedStatement.setString(8, positie); // positie

            // De query uitvoeren
            preparedStatement.executeUpdate();
            System.out.println("Speler succesvol opgeslagen in de database!");

        } catch (Exception e) {
            // Fouten afdrukken bij eventuele problemen
            e.printStackTrace();
        }
    }

    public void bewerkSpeler(Speler s) {

        // Query met behulp van een prepared statement
        String query = "UPDATE speler SET spelerNaam = ?, rugnummer = ?, nationaliteit = ?, aantalGoals = ?, aantalAssists = ?, profielfoto = ?, club = ?, positie = ? WHERE spelerID = ?";

        try (PreparedStatement preparedStatement = this.conn.prepareStatement(query)) {
            // Parameters instellen voor het prepared statement
            preparedStatement.setString(1, s.getSpelerNaam());
            preparedStatement.setInt(2, s.getRugnummer());
            preparedStatement.setInt(3, getNationaliteitID(s.getNationaliteit()));
            preparedStatement.setInt(4, s.getAantalGoals());
            preparedStatement.setInt(5, s.getAantalAssists());
            preparedStatement.setBlob(6, s.getProfielfoto());
            preparedStatement.setInt(7, getClubID(s.getClub()));
            preparedStatement.setInt(8, getPositieID(s.getPositie()));
            preparedStatement.setInt(9, s.getId());

            // De update uitvoeren
            preparedStatement.executeUpdate();

            System.out.println("Speler succesvol bijgewerkt in de database!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void verwijderSpeler(Speler s){

        // Query schrijven
        String query = "DELETE FROM speler WHERE spelerID = " + s.getId();

        try {
            // Statement-object aanmaken
            Statement stm = this.conn.createStatement();
            // Query uitvoeren
            stm.execute(query);
            System.out.println("Speler succesvol verwijderd uit de database!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
