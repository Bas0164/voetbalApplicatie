package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import com.bas.voetbalapplicatie.classes.Database;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

public class ClubsToevoegen {

    String encodedString; // Variabele om de gecodeerde afbeeldingsstring op te slaan

    public ClubsToevoegen() {
        Database db = new Database(); // Instantie van de Database-klasse
        Stage stage = new Stage(); // Het hoofdvenster van de applicatie
        VBox root = new VBox(); // Het hoofdlay-outpaneel
        root.setId("root"); // Toekennen van een ID aan het paneel
        Scene scene = new Scene(root, 800, 600); // Het hoofdscene-object
        stage.setScene(scene); // Toekennen van de scene aan het hoofdvenster
        stage.setTitle("Club toevoegen"); // Titel van het hoofdvenster
        stage.show(); // Weergeven van het hoofdvenster
        stage.setResizable(false); // Zorgen dat je het scherm niet kan aanpassen qua grootte

        // Toevoegen van externe stijlbladen
        scene.getStylesheets().add(Application.class.getResource("stylesheets/clubsToevoegen.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        HBox clubNaamHbox = new HBox();
        Label clubNaamLabel = new Label("Clubnaam: "); // Label voor de clubnaam
        TextField clubNaam = new TextField(); // Tekstinvoerveld voor de clubnaam

        HBox logoHbox = new HBox();
        Label labelLogo = new Label("Logo: "); // Label voor het logo
        Button uploadLogo = new Button("Upload"); // Knop om een afbeelding te uploaden
        uploadLogo.setId("uploadLogo");
        uploadLogo.setOnMouseEntered(event -> {
            uploadLogo.setCursor(Cursor.HAND);
        });

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

        HBox stadionHbox = new HBox();
        Label stadionLabel = new Label("Stadion: "); // Label voor het stadion
        ComboBox<String> stadion = new ComboBox<>(); // Keuzelijst voor stadionnamen
        stadion.setOnMouseEntered(event -> {
            stadion.setCursor(Cursor.HAND);
        });
        try {
            // Maak verbinding met de database en haal stadionnamen op
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT stadionNaam FROM stadion");
            while (resultSet.next()) {
                String item = resultSet.getString("stadionNaam");
                stadion.getItems().add(item); // Voeg stadionnamen toe aan de keuzelijst
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0));

        Button btnOpslaan = new Button("Opslaan"); // Knop om de clubgegevens op te slaan
        btnOpslaan.setId("btnOpslaan");
        btnOpslaan.setOnAction(e -> {
            if (!clubNaam.getText().isEmpty() && encodedString != null && stadion.getValue() != null) {
            String SclubNaam = clubNaam.getText(); // Clubnaam ophalen
            String Slogo = encodedString; // Gecodeerde afbeeldingsstring ophalen
            String selectedStadionNaam = stadion.getValue(); // Geselecteerde stadionnaam ophalen
            db.opslaanClub(SclubNaam, Slogo, selectedStadionNaam); // Opslaan van clubgegevens
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
            Clubs ac = new Clubs();
            stage.close();
        });
        terugKnop.setOnMouseEntered(event -> {
            terugKnop.setCursor(Cursor.HAND);
        });

        VBox.setMargin(clubNaamHbox, new javafx.geometry.Insets(140, 0, 30, 200));
        VBox.setMargin(logoHbox, new javafx.geometry.Insets(0, 0, 30, 245));
        VBox.setMargin(stadionHbox, new javafx.geometry.Insets(0, 0, 120, 220));

        // Elementen toevoegen aan het hoofdlay-outpaneel
        root.getChildren().addAll(clubNaamHbox, logoHbox, stadionHbox, buttons);
        clubNaamHbox.getChildren().addAll(clubNaamLabel, clubNaam);
        logoHbox.getChildren().addAll(labelLogo, uploadLogo);
        stadionHbox.getChildren().addAll(stadionLabel, stadion);
        buttons.getChildren().addAll(btnOpslaan, terugKnop);
    }
}
