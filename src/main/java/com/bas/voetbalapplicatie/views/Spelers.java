package com.bas.voetbalapplicatie.views;
import com.bas.voetbalapplicatie.Application;
import com.bas.voetbalapplicatie.classes.Speler;
import com.bas.voetbalapplicatie.classes.Database;
import com.bas.voetbalapplicatie.classes.TabelSpelerFoto;
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

import java.sql.Blob;

public class Spelers {

    public Spelers(){

        // Maak een instantie van de Database-klasse
        Database db = new Database();

        // Maak een nieuw venster
        Stage stage = new Stage();
        stage.setResizable(false);

        // Maak een GridPane als de hoofdcontainer voor de scene
        FlowPane root = new FlowPane();
        root.setId("root");

        // Maak een nieuwe scene met de GridPane, en stel de grootte in op 800x600 pixels
        Scene scene = new Scene(root, 1440, 800);
        stage.setScene(scene);

        // Stel de titel van het venster in
        stage.setTitle("Alle spelers");

        // Toon het venster
        stage.show();

        // Voeg externe stylesheets toe aan de scene
        scene.getStylesheets().add(Application.class.getResource("stylesheets/spelers.css").toString());
        scene.getStylesheets().add(Application.class.getResource("fonts/Oswald-Medium.ttf").toString());

        // Maak een TableView aan voor de spelers
        TableView<Speler> spelersTable = new TableView<>();
        spelersTable.setId("spelersTable");

        // Definieer kolommen voor de TableView
        TableColumn<Speler, String> col0 = new TableColumn<>("Naam");
        col0.setCellValueFactory(new PropertyValueFactory<>("spelerNaam"));
        col0.setPrefWidth(400);

        TableColumn<Speler, Integer> col1 = new TableColumn<>("Rugnummer");
        col1.setCellValueFactory(new PropertyValueFactory<>("rugnummer"));
        col1.setPrefWidth(80);

        TableColumn<Speler, String> col2 = new TableColumn<>("Nationaliteit");
        col2.setCellValueFactory(new PropertyValueFactory<>("nationaliteit"));
        col2.setPrefWidth(250);

        TableColumn<Speler, Integer> col3 = new TableColumn<>("Aantal goals");
        col3.setCellValueFactory(new PropertyValueFactory<>("aantalGoals"));
        col3.setPrefWidth(80);

        TableColumn<Speler, Integer> col4 = new TableColumn<>("Aantal assists");
        col4.setCellValueFactory(new PropertyValueFactory<>("aantalAssists"));
        col4.setPrefWidth(80);

        TableColumn<Speler, Blob> col5 = new TableColumn<>("Profielfoto");
        col5.setCellValueFactory(new PropertyValueFactory<>("profielfoto"));
        col5.setCellFactory(column -> new TabelSpelerFoto());
        col5.setPrefWidth(100);

        TableColumn<Speler, String> col6 = new TableColumn<>("Club");
        col6.setCellValueFactory(new PropertyValueFactory<>("club"));
        col6.setPrefWidth(200);

        TableColumn<Speler, String> col7 = new TableColumn<>("Positie");
        col7.setCellValueFactory(new PropertyValueFactory<>("positie"));
        col7.setPrefWidth(250);

        // Voeg de kolommen toe aan de TableView
        spelersTable.getColumns().addAll(col0, col1, col2, col3, col4, col5, col6, col7);

        // Vul de TableView met clubgegevens uit de database
        spelersTable.getItems().addAll(db.laatSpelersZien());

        // Open een nieuw scherm wanneer er op een speler wordt geklikt
//        spelersTable.setOnMouseClicked(e -> {
//            Speler s = (Speler) spelersTable.getSelectionModel().getSelectedItem();
//            // Openen nieuw scherm
//            SpelersBewerken sb = new SpelersBewerken(s);
//        });

        // Creëer HBox voor knoppen
        HBox buttons = new HBox(100);
        buttons.setId("buttons");
        HBox.setMargin(buttons, new Insets(100, 0, 0, 0));

        // Maak een knop om een nieuwe speler toe te voegen
        Button spelerToevoegen = new Button("Speler toevoegen");
        spelerToevoegen.setId("spelerToevoegen");
        spelerToevoegen.setOnAction(e -> {
            // Wanneer de knop wordt geklikt, maak een nieuw venster om een speler toe te voegen
            SpelersToevoegen st = new SpelersToevoegen();
            stage.close();
        });
        spelerToevoegen.setOnMouseEntered(event -> {
            spelerToevoegen.setCursor(Cursor.HAND);
        });

        // Maak een knop om terug te gaan naar het startscherm
        Button terugKnop = new Button("Terug");
        terugKnop.setId("terugKnop");
        terugKnop.setOnAction(e -> {
            stage.close();
        });
        terugKnop.setOnMouseEntered(event -> {
            terugKnop.setCursor(Cursor.HAND);
        });

        // Voeg de TableView en de knoppen toe aan de GridPane
        root.getChildren().addAll(spelersTable, buttons);
        buttons.getChildren().addAll(spelerToevoegen, terugKnop);
    }
}