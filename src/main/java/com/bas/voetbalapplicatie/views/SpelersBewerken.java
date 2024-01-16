package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import com.bas.voetbalapplicatie.classes.Club;
import com.bas.voetbalapplicatie.classes.Database;
import com.bas.voetbalapplicatie.classes.Speler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class SpelersBewerken {

    String encodedString; // Variabele om de gecodeerde afbeeldingsstring op te slaan

    public SpelersBewerken(Speler s) {

    Stage stage = new Stage();
    VBox root = new VBox();
        root.setId("root");
    Scene scene = new Scene(root, 1440, 800);

        stage.setScene(scene);
        stage.setTitle("Speler bewerken");
        stage.show();

    // Voeg externe stylesheets toe aan de scene
        scene.getStylesheets().add(Application .class.getResource("stylesheets/spelersBewerken.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        HBox spelerNaamHbox = new HBox();
        Label spelerNaamLabel = new Label("Spelernaam: "); // Label voor de spelerNaam
        TextField spelerNaam = new TextField(); // Tekstinvoerveld voor de spelerNaam
        spelerNaam.setText(s.getSpelerNaam());

        HBox rugnummerHbox = new HBox();
        Label rugnummerLabel = new Label("Rugnummer: "); // Label voor het rugnummer
        TextField rugnummer = new TextField(); // Tekstinvoerveld voor het rugnummer
        rugnummer.setText(String.valueOf(s.getRugnummer()));

        HBox nationaliteitHbox = new HBox();
        Label nationaliteitLabel = new Label("Nationaliteit: "); // Label voor de nationaliteit
        ComboBox<String> nationaliteit = new ComboBox<>(); // Keuzelijst voor nationaliteiten
        nationaliteit.setValue(s.getNationaliteit());
        nationaliteit.setOnMouseEntered(event -> {
            nationaliteit.setCursor(Cursor.HAND);
        });
        try {
            // Maak verbinding met de database en haal stadionnamen op
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nationaliteit FROM nationaliteit");
            while (resultSet.next()) {
                String item = resultSet.getString("nationaliteit");
                nationaliteit.getItems().add(item); // Voeg nationaliteiten toe aan de keuzelijst
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox aantalGoalsHbox = new HBox();
        Label aantalGoalsLabel = new Label("Aantal goals: "); // Label voor het aantal goals
        TextField aantalGoals = new TextField(); // Tekstinvoerveld voor het aantal goals
        aantalGoals.setText(String.valueOf(s.getAantalGoals())); // Converteer naar String voordat je instelt

        HBox aantalAssistsHbox = new HBox();
        Label aantalAssistsLabel = new Label("Aantal assists: "); // Label voor het aantal assists
        TextField aantalAssists = new TextField(); // Tekstinvoerveld voor het aantal assists
        aantalAssists.setText(String.valueOf(s.getAantalAssists())); // Converteer naar String voordat je instelt

        HBox profielfotoHbox = new HBox();
        Label labelProfielfoto = new Label("Profielfoto: "); // Label voor de profielfoto
        Button uploadProfielfoto = new Button("Upload"); // Knop om een afbeelding te uploaden
        uploadProfielfoto.setId("uploadProfielfoto");
        uploadProfielfoto.setOnMouseEntered(event -> {
            uploadProfielfoto.setCursor(Cursor.HAND);
        });

        uploadProfielfoto.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser(); // Dialoogvenster voor bestandsselectie
            fileChooser.setTitle("Selecteer Afbeelding");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Afbeeldingsbestanden", "*.png", "*.jpg", "*.jpeg")
            );
            Path selectedFile = fileChooser.showOpenDialog(stage).toPath(); // Geselecteerd bestand
            try {
                byte[] fileContent = Files.readAllBytes(selectedFile);
                encodedString = Base64.getEncoder().encodeToString(fileContent); // Omzetten naar Base64-gecodeerde string
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox clubHbox = new HBox();
        Label clubLabel = new Label("Club: "); // Label voor de club
        ComboBox<String> club = new ComboBox<>(); // Keuzelijst voor clubs
        club.setValue(s.getClub());
        club.setOnMouseEntered(event -> {
            club.setCursor(Cursor.HAND);
        });
        try {
            // Maak verbinding met de database en haal clubs op
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT clubNaam FROM club");
            while (resultSet.next()) {
                String item = resultSet.getString("clubNaam");
                club.getItems().add(item); // Voeg clubs toe aan de keuzelijst
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox positieHbox = new HBox();
        Label positieLabel = new Label("Positie: "); // Label voor de positie
        ComboBox<String> positie = new ComboBox<>(); // Keuzelijst voor posities
        positie.setValue(s.getPositie());
        positie.setOnMouseEntered(event -> {
            club.setCursor(Cursor.HAND);
        });
        try {
            // Maak verbinding met de database en haal posities op
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT positie FROM positie");
            while (resultSet.next()) {
                String item = resultSet.getString("positie");
                positie.getItems().add(item); // Voeg posities toe aan de keuzelijst
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    HBox logoHbox = new HBox();
    Label labelLogo = new Label("Logo:"); // Label voor het logo
    Button uploadLogo = new Button("Upload"); // Knop om een afbeelding te uploaden
        uploadLogo.setId("uploadLogo");
        uploadLogo.setOnAction(event -> {
        FileChooser fileChooser = new FileChooser(); // Dialoogvenster voor bestandsselectie
        fileChooser.setTitle("Selecteer Afbeelding");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Afbeeldingsbestanden", "*.png", "*.jpg", "*.jpeg")
        );
        Path selectedFile = fileChooser.showOpenDialog(stage).toPath(); // Geselecteerd bestand
        try {
            byte[] fileContent = Files.readAllBytes(selectedFile);
            encodedString = Base64.getEncoder().encodeToString(fileContent); // Omzetten naar Base64-gecodeerde string
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

        VBox.setMargin(spelerNaamHbox, new javafx.geometry.Insets(100, 0, 30, 508));
        VBox.setMargin(rugnummerHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(nationaliteitHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(aantalGoalsHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(aantalAssistsHbox, new javafx.geometry.Insets(0, 0, 30, 490));
        VBox.setMargin(profielfotoHbox, new javafx.geometry.Insets(0, 0, 30, 510));
        VBox.setMargin(clubHbox, new javafx.geometry.Insets(0, 0, 30, 570));
        VBox.setMargin(positieHbox, new javafx.geometry.Insets(0, 0, 50, 550));

    HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0));

    Button btnDelete = new Button("Verwijder");
        btnDelete.setId("btnDelete");

        btnDelete.setOnAction(e -> {
        // Toon een bevestigingsdialoog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestiging");
        alert.setHeaderText("Weet je zeker dat je deze speler wilt verwijderen?");

        // Voeg knoppen toe aan de dialoog
        ButtonType buttonTypeYes = new ButtonType("Ja");
        ButtonType buttonTypeNo = new ButtonType("Nee");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wacht op gebruikersinvoer
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                // Gebruiker heeft "Ja" gekozen, verwijder de club
                Database db = new Database();
                db.verwijderSpeler(s);
                stage.close();
            } else {
                // Gebruiker heeft "Nee" gekozen, doe niets
            }
        });
    });

    Button btnWijzig = new Button("Wijzig");
        btnWijzig.setId("btnWijzig");
        btnWijzig.setOnAction(e -> {
            if (!spelerNaam.getText().isEmpty() && !rugnummer.getText().isEmpty() && nationaliteit.getValue() != null && !aantalGoals.getText().isEmpty() && !aantalAssists.getText().isEmpty() && encodedString != null && club.getValue() != null && positie.getValue() != null) {
                s.setSpelerNaam(spelerNaam.getText());
            try {
                int rugnummerValue = Integer.parseInt(rugnummer.getText());
                s.setRugnummer(rugnummerValue);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            s.setNationaliteit(nationaliteit.getValue());
            try {
                int aantalGoalsValue = Integer.parseInt(aantalGoals.getText());
                s.setAantalGoals(aantalGoalsValue);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            try {
                int aantalAssistsValue = Integer.parseInt(aantalAssists.getText());
                s.setAantalAssists(aantalAssistsValue);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            s.setClub(club.getValue());
        s.setPositie(positie.getValue());
        Database db = new Database();
        db.bewerkSpeler(s);
        stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText((String)null);
                alert.setContentText("Vul alle velden in!");
                alert.showAndWait();
            }
    });

    Button terugKnop = new Button("Terug");
        terugKnop.setId("terugKnop");
        terugKnop.setOnAction(e -> {
        stage.close();
    });
        terugKnop.setOnMouseEntered(event -> {
        terugKnop.setCursor(Cursor.HAND);
    });

        root.getChildren().addAll(spelerNaamHbox, rugnummerHbox, nationaliteitHbox, aantalGoalsHbox, aantalAssistsHbox, profielfotoHbox, clubHbox, positieHbox, buttons);
        spelerNaamHbox.getChildren().addAll(spelerNaamLabel, spelerNaam);
        rugnummerHbox.getChildren().addAll(rugnummerLabel, rugnummer);
        nationaliteitHbox.getChildren().addAll(nationaliteitLabel, nationaliteit);
        aantalGoalsHbox.getChildren().addAll(aantalGoalsLabel, aantalGoals);
        aantalAssistsHbox.getChildren().addAll(aantalAssistsLabel, aantalAssists);
        profielfotoHbox.getChildren().addAll(labelProfielfoto, uploadProfielfoto);
        clubHbox.getChildren().addAll(clubLabel, club);
        positieHbox.getChildren().addAll(positieLabel, positie);
        buttons.getChildren().addAll(btnWijzig, btnDelete, terugKnop);
}
}
