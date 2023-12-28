package com.bas.voetbalapplicatie;

import com.bas.voetbalapplicatie.classes.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Homescreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database db = new Database();

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Homescreen");
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}