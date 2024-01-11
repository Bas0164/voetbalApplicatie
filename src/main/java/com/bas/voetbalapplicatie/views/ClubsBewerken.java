package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.bas.voetbalapplicatie.classes.Database;
import com.bas.voetbalapplicatie.classes.Club;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class ClubsBewerken {

    String encodedString; // Variabele om de gecodeerde afbeeldingsstring op te slaan

    public ClubsBewerken(Club c) {

        Stage stage = new Stage();
        VBox root = new VBox();
        root.setId("root");
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Club bewerken");
        stage.show();

        // Voeg externe stylesheets toe aan de scene
        scene.getStylesheets().add(Application.class.getResource("stylesheets/clubsBewerken.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        HBox clubNaamHbox = new HBox();
        Label clubNaamLabel = new Label("Clubnaam:"); // Label voor de clubnaam
        TextField clubNaam = new TextField();
        clubNaam.setText(c.getClubNaam());

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

        HBox stadionHbox = new HBox();
        Label stadionLabel = new Label("Stadion:"); // Label voor het stadion
        ComboBox<String> stadion = new ComboBox<>(); // Keuzelijst voor stadionnamen
        stadion.setValue(c.getStadion());
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

        VBox.setMargin(clubNaamHbox, new javafx.geometry.Insets(140, 0, 30, 200));
        VBox.setMargin(logoHbox, new javafx.geometry.Insets(0, 0, 30, 200));
        VBox.setMargin(stadionHbox, new javafx.geometry.Insets(0, 0, 120, 200));

        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0));

        Button btnDelete = new Button("Verwijder");
        btnDelete.setId("btnDelete");
        Database db = new Database();
        btnDelete.setOnAction(e -> {
            db.verwijderClub(c);
            stage.close();
        });

        Button btnWijzig = new Button("Wijzig");
        btnWijzig.setId("btnWijzig");
        btnWijzig.setOnAction(e -> {
            c.setClubNaam(clubNaam.getText());
            //v.setPlaats(txtPlaats.getText());
            c.setStadion(stadion.getValue());
            db.bewerkClub(c);
            stage.close();
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

        root.getChildren().addAll(clubNaamHbox, logoHbox, stadionHbox, buttons);
        clubNaamHbox.getChildren().addAll(clubNaamLabel, clubNaam);
        logoHbox.getChildren().addAll(labelLogo, uploadLogo);
        stadionHbox.getChildren().addAll(stadionLabel, stadion);
        buttons.getChildren().addAll(btnWijzig, btnDelete, terugKnop);
    }
}
