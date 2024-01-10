package com.bas.voetbalapplicatie.views;

import com.bas.voetbalapplicatie.Application;
import com.bas.voetbalapplicatie.classes.Club;
import com.bas.voetbalapplicatie.classes.Database;
import com.bas.voetbalapplicatie.classes.TabelFoto;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Blob;

public class Clubs {
    public Clubs() {
        // Maak een instantie van de Database-klasse
        Database db = new Database();

        // Maak een nieuw venster
        Stage stage = new Stage();
        stage.setResizable(false);

        // Maak een GridPane als de hoofdcontainer voor de scene
        FlowPane root = new FlowPane();
        root.setId("root");

        // Maak een nieuwe scene met de GridPane, en stel de grootte in op 800x600 pixels
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        // Stel de titel van het venster in
        stage.setTitle("Alle clubs");

        // Toon het venster
        stage.show();

        // Voeg externe stylesheets toe aan de scene
        scene.getStylesheets().add(Application.class.getResource("stylesheets/clubs.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        // Maak een TableView aan voor de clubs
        TableView<Club> clubsTable = new TableView<>();
        clubsTable.setId("clubsTable");

        // Definieer kolommen voor de TableView
        TableColumn<Club, String> col0 = new TableColumn<>("Clubnaam");
        col0.setCellValueFactory(new PropertyValueFactory<>("clubNaam"));
        col0.setPrefWidth(300);

        TableColumn<Club, Blob> col1 = new TableColumn<>("Logo");
        col1.setCellValueFactory(new PropertyValueFactory<>("logo"));
        col1.setCellFactory(column -> new TabelFoto());
        col1.setPrefWidth(200);

        TableColumn<Club, String> col2 = new TableColumn<>("Stadion");
        col2.setCellValueFactory(new PropertyValueFactory<>("stadion"));
        col2.setPrefWidth(300);

        // Voeg de kolommen toe aan de TableView
        clubsTable.getColumns().addAll(col0, col1, col2);

        // Vul de TableView met clubgegevens uit de database
        clubsTable.getItems().addAll(db.laatClubsZien());

        // Open een nieuw scherm wanneer er op een club wordt geklikt
        clubsTable.setOnMouseClicked(e -> {
            Club c = (Club) clubsTable.getSelectionModel().getSelectedItem();
            // Openen nieuw scherm
            ClubsBewerken mv = new ClubsBewerken(c);
        });

        // Creëer HBox voor knoppen
        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0));

        // Maak een knop om een nieuwe club toe te voegen
        Button clubToevoegen = new Button("Club toevoegen");
        clubToevoegen.setId("clubToevoegen");
        clubToevoegen.setOnAction(e -> {
            // Wanneer de knop wordt geklikt, maak een nieuw venster om een club toe te voegen
            ClubsToevoegen ct = new ClubsToevoegen();
        });
        clubToevoegen.setOnMouseEntered(event -> {
            clubToevoegen.setCursor(Cursor.HAND);
        });

        // Maak een knop om terug te gaan naar het startscherm
        Button terugKnop = new Button("Terug");
        terugKnop.setId("terugKnop");
        terugKnop.setOnAction(e -> {
            HomeScreen hs = new HomeScreen();
            stage.setScene(hs.getScene());
        });

        terugKnop.setOnMouseEntered(event -> {
            terugKnop.setCursor(Cursor.HAND);
        });

        // Voeg de TableView en de knoppen toe aan de GridPane
        root.getChildren().addAll(clubsTable, buttons);
        buttons.getChildren().addAll(clubToevoegen, terugKnop);
    }
}
