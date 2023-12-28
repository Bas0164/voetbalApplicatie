module com.bas.voetbalapplicatie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; //Zorgt ervoor dat je sql kan gebruiken



    opens com.bas.voetbalapplicatie to javafx.fxml;
    exports com.bas.voetbalapplicatie;
    exports com.bas.voetbalapplicatie.classes;//Maakt de classes package overal bruikbaar
}