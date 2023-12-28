package com.bas.voetbalapplicatie.views;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Spelers {

    public Spelers(){

        Stage stage = new Stage();
        GridPane root = new GridPane();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Alle spelers");
        stage.show();

    }
}
