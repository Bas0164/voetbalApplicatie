package com.bas.voetbalapplicatie.classes;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class TabelClubLogo extends TableCell<Club, Blob> {

    // ImageView om de afbeelding weer te geven
    private final ImageView imageView = new ImageView();

    // Gewenste breedte van de afbeelding
    private final int IMAGE_WIDTH = 50; // Pas de breedte van de afbeelding naar wens aan

    // Gewenste hoogte van de afbeelding
    private final int IMAGE_HEIGHT = 50; // Pas de hoogte van de afbeelding naar wens aan

    @Override
    protected void updateItem(Blob blob, boolean empty) {
        super.updateItem(blob, empty);

        // Zorg ervoor dat de cel leeg is voordat we iets toevoegen
        if (empty || blob == null) {
            setGraphic(null);
        } else {
            try {
                // Converteer de Blob naar een byte-array
                byte[] imageBytes = blob.getBytes(1, (int) blob.length());

                // Converteer de byte-array naar een Image
                Image image = new Image(new ByteArrayInputStream(imageBytes), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);

                // Zet de Image in de ImageView en toon het in de cel
                imageView.setImage(image);
                setGraphic(imageView);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
