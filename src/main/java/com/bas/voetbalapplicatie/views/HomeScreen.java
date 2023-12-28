package com.bas.voetbalapplicatie.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeScreen {
    private Scene scene;
    public HomeScreen(){

        GridPane root = new GridPane();
        scene = new Scene(root, 800, 600);


    }

    public Scene getScene() {
        return scene;
    }
}