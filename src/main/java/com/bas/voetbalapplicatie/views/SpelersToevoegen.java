package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import com.bas.voetbalapplicatie.classes.Database;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;

public class SpelersToevoegen {

    String encodedString; // Variabele om de gecodeerde afbeeldingsstring op te slaan

    public SpelersToevoegen() {
        Database db = new Database(); // Instantie van de Database-klasse
        Stage stage = new Stage(); // Het hoofdvenster van de applicatie
        VBox root = new VBox(); // Het hoofdlay-outpaneel
        root.setId("root"); // Toekennen van een ID aan het paneel
        Scene scene = new Scene(root, 1440, 800); // Het hoofdscene-object
        stage.setScene(scene); // Toekennen van de scene aan het hoofdvenster
        stage.setTitle("Speler toevoegen"); // Titel van het hoofdvenster
        stage.show(); // Weergeven van het hoofdvenster
        stage.setResizable(false); // Zorgen dat je het scherm niet kan aanpassen qua grootte

        // Toevoegen van externe stijlbladen
        scene.getStylesheets().add(Application.class.getResource("stylesheets/spelersToevoegen.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        HBox spelerNaamHbox = new HBox();
        Label spelerNaamLabel = new Label("Spelernaam: "); // Label voor de spelerNaam
        TextField spelerNaam = new TextField(); // Tekstinvoerveld voor de spelerNaam

        HBox rugnummerHbox = new HBox();
        Label rugnummerLabel = new Label("Rugnummer: "); // Label voor het rugnummer
        TextField rugnummer = alleenNummerInvoer(2); // Tekstinvoerveld voor het rugnummer (met maximaal invoer van 2 nummers)

        HBox nationaliteitHbox = new HBox();
        Label nationaliteitLabel = new Label("Nationaliteit: "); // Label voor de nationaliteit
        ComboBox<String> nationaliteit = new ComboBox<>(); // Keuzelijst voor nationaliteiten

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
        TextField aantalGoals = alleenNummerInvoer(4); // Tekstinvoerveld voor het aantal goals (met maximaal invoer van 4 nummers)

        HBox aantalAssistsHbox = new HBox();
        Label aantalAssistsLabel = new Label("Aantal assists: "); // Label voor het aantal assists
        TextField aantalAssists = alleenNummerInvoer(4); // Tekstinvoerveld voor het aantal assists (met maximaal invoer van 4 nummers)

        // ImageView voor het weergeven van de profielfoto
        ImageView profielfotoImageView = new ImageView();
        profielfotoImageView.setFitHeight(75); // Pas de hoogte aan zoals gewenst
        profielfotoImageView.setFitWidth(75); // Pas de breedte aan zoals gewenst

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

                // Laad de afbeelding in de ImageView
                Image profielfotoImage = new Image("data:image/png;base64," + encodedString);
                profielfotoImageView.setImage(profielfotoImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox clubHbox = new HBox();
        Label clubLabel = new Label("Club: "); // Label voor de club
        ComboBox<String> club = new ComboBox<>(); // Keuzelijst voor clubs
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

        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(200, 0, 0, 0));

        Button btnOpslaan = new Button("Opslaan"); // Knop om de spelergegevens op te slaan
        btnOpslaan.setId("btnOpslaan");
        btnOpslaan.setOnAction(e -> {
            if (!spelerNaam.getText().isEmpty() && !rugnummer.getText().isEmpty() && nationaliteit.getValue() != null && !aantalGoals.getText().isEmpty() && !aantalAssists.getText().isEmpty() && encodedString != null && club.getValue() != null && positie.getValue() != null) {
            String SspelerNaam = spelerNaam.getText(); // spelernaam ophalen
            Integer Irugnummer = Integer.parseInt(rugnummer.getText()); //rugnummer ophalen
            String Snationaliteit = nationaliteit.getValue(); // Geselecteerde nationaliteit ophalen
            Integer IaantalGoals = Integer.parseInt(aantalGoals.getText()); //aantal goals ophalen
            Integer IaantalAssists = Integer.parseInt(aantalAssists.getText()); //aantal assists ophalen
            String Sprofielfoto = encodedString; // Gecodeerde afbeeldingsstring ophalen
            String Sclub = club.getValue(); // Geselecteerde club ophalen
            String Spositie = positie.getValue(); // Geselecteerde positie ophalen
            db.opslaanSpeler(SspelerNaam, Irugnummer, Snationaliteit, IaantalGoals, IaantalAssists, Sprofielfoto, Sclub, Spositie); // Opslaan van spelergegevens
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText((String)null);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        }
        });
        btnOpslaan.setOnMouseEntered(event -> {
            btnOpslaan.setCursor(Cursor.HAND);
        });

        Button terugKnop = new Button("Terug");
        terugKnop.setId("terugKnop");
        terugKnop.setOnAction(e -> {
            Spelers ac = new Spelers();
            stage.close();
        });
        terugKnop.setOnMouseEntered(event -> {
            terugKnop.setCursor(Cursor.HAND);
        });

        VBox.setMargin(spelerNaamHbox, new javafx.geometry.Insets(100, 0, 30, 508));
        VBox.setMargin(rugnummerHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(nationaliteitHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(aantalGoalsHbox, new javafx.geometry.Insets(0, 0, 30, 500));
        VBox.setMargin(aantalAssistsHbox, new javafx.geometry.Insets(0, 0, 30, 490));
        VBox.setMargin(profielfotoHbox, new javafx.geometry.Insets(0, 0, 30, 510));
        VBox.setMargin(clubHbox, new javafx.geometry.Insets(0, 0, 30, 570));
        VBox.setMargin(positieHbox, new javafx.geometry.Insets(0, 0, 50, 550));

        // Elementen toevoegen aan het hoofdlay-outpaneel
        root.getChildren().addAll(spelerNaamHbox, rugnummerHbox, nationaliteitHbox, aantalGoalsHbox, aantalAssistsHbox, profielfotoHbox, clubHbox, positieHbox, buttons);
        spelerNaamHbox.getChildren().addAll(spelerNaamLabel, spelerNaam);
        rugnummerHbox.getChildren().addAll(rugnummerLabel, rugnummer);
        nationaliteitHbox.getChildren().addAll(nationaliteitLabel, nationaliteit);
        aantalGoalsHbox.getChildren().addAll(aantalGoalsLabel, aantalGoals);
        aantalAssistsHbox.getChildren().addAll(aantalAssistsLabel, aantalAssists);
        profielfotoHbox.getChildren().addAll(labelProfielfoto, uploadProfielfoto, profielfotoImageView);
        clubHbox.getChildren().addAll(clubLabel, club);
        positieHbox.getChildren().addAll(positieLabel, positie);
        buttons.getChildren().addAll(btnOpslaan, terugKnop);
    }

    private TextField alleenNummerInvoer(int maxLength) {
        TextField textField = new TextField();

        // Maak een TextFormatter met een filter voor numerieke waarden en maximaal aantal cijfers
        TextFormatter<Integer> formatter = new TextFormatter<>(
                new IntegerStringConverter(),
                null,
                change -> {
                    String newText = change.getControlNewText();

                    // Controleer of de nieuwe tekst alleen cijfers bevat en niet langer is dan maxLength
                    if (newText.matches("\\d*") && newText.length() <= maxLength) {
                        return change;
                    }

                    return null;
                }
        );

        // Koppel de TextFormatter aan het TextField
        textField.setTextFormatter(formatter);

        return textField;
    }
}
