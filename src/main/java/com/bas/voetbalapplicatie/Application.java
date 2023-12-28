package com.bas.voetbalapplicatie;
import com.bas.voetbalapplicatie.views.HomeScreen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

//        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));

        HomeScreen homeScreen = new HomeScreen();

        stage.setTitle("Voetbal Applicatie");
        stage.setScene(homeScreen.getScene());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}