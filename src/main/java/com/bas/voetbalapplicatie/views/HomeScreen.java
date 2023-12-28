package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class HomeScreen {
    private Scene scene;

    // Constructor voor het startscherm van de applicatie
    public HomeScreen() {
        // Creëer een FlowPane als hoofdcontainer voor het startscherm
        FlowPane root = new FlowPane();
        root.setId("root");
        scene = new Scene(root, 800, 600);

        // Voeg externe stijlbladen toe aan de scene
        scene.getStylesheets().add(Application.class.getResource("stylesheets/homescreen.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        // Creëer een verticale box (VBox) voor het verticale menu (vMenu)
        VBox vMenu = new VBox();
        vMenu.setId("vMenu");

        // Label met de naam van de applicatie
        Label applicatieNaam = new Label("Voetbal Applicatie");
        applicatieNaam.setId("applicatieNaam");

        // Horizontale box (HBox) voor knoppen
        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0)); // Ruimte boven de knoppen

        // Knop voor alle clubs
        Button alleClubsButton = new Button("Alle clubs");
        alleClubsButton.setId("alleClubsButton");
        alleClubsButton.setOnMouseClicked(e -> {
            Clubs ac = new Clubs();
        });

        // Knop voor alle spelers
        Button alleSpelersButton = new Button("Alle spelers");
        alleSpelersButton.setId("alleSpelersButton");
        alleSpelersButton.setOnMouseClicked(e -> {
            Spelers as = new Spelers();
        });

        // Label met copyrightinformatie
        Label copyrightText = new Label("Copyright \u00A9 2023 | Deze applicatie is gemaakt door Bas de Bruijn");
        copyrightText.setId("copyrightText");

        // Voeg de elementen toe aan de hoofdcontainer
        root.getChildren().add(vMenu);
        vMenu.getChildren().add(applicatieNaam);
        vMenu.getChildren().add(buttons);
        vMenu.getChildren().add(copyrightText);
        buttons.getChildren().addAll(alleSpelersButton, alleClubsButton);
    }

    // Methode om de JavaFX Scene op te halen
    public Scene getScene() {
        return scene;
    }
}