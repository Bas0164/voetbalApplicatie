package com.bas.voetbalapplicatie.classes;

import org.junit.jupiter.api.Test;

import java.io.File;

class DatabaseTest {

    @Test
    void laatClubsZien() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Roep de methode 'laatClubsZien' aan om de clubs te tonen
        db.laatClubsZien();
    }

    @Test
    void opslaanClub() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Maak een File-object met het pad naar het Feyenoord-logo
        File file = new File("C:\\Users\\basde\\Desktop\\voetbalApplicatie\\src\\main\\resources\\com\\bas\\voetbalapplicatie\\images\\feyenoord.png");

        // Roep de methode 'opslaanClub' aan om een club op te slaan
        db.opslaanClub("Feyenoord", file.toPath(), "de Kuip");
    }

    @Test
    void bewerkClub() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Maak een File-object met het pad naar het Feyenoord-logo
        File file = new File("C:\\Users\\basde\\Desktop\\voetbalApplicatie\\src\\main\\resources\\com\\bas\\voetbalapplicatie\\images\\feyenoord.png");

        // Roep de methode 'opslaanClub' aan om een club op te slaan (lijkt op een duplicaat, controleer of dit de bedoeling is)
        db.opslaanClub("Feyenoord", file.toPath(), "de Kuip");
    }

    @Test
    void laatSpelersZien() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Roep de methode 'laatSpelersZien' aan om de spelers te tonen
        db.laatSpelersZien();
    }

    @Test
    void opslaanSpeler() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Maak een File-object met het pad naar de afbeelding van Santiage Gimenez
        File file = new File("C:\\Users\\basde\\Desktop\\voetbalApplicatie\\src\\main\\resources\\com\\bas\\voetbalapplicatie\\images\\gimenez.png");

        // Roep de methode 'opslaanSpeler' aan om een speler op te slaan
        db.opslaanSpeler("Santiago Gimenez", 29, "Mexico", 18, 4, file.toPath(), "Feyenoord", "Aanvaller");
    }

    @Test
    void bewerkSpeler() {
        // Instantieer een object van de Database-klasse
        Database db = new Database();

        // Maak een File-object met het pad naar de afbeelding van Santiage Gimenez
        File file = new File("C:\\Users\\basde\\Desktop\\voetbalApplicatie\\src\\main\\resources\\com\\bas\\voetbalapplicatie\\images\\gimenez.png");

        // Roep de methode 'opslaanSpeler' aan om een speler op te slaan (lijkt op een duplicaat, controleer of dit de bedoeling is)
        db.opslaanSpeler("Santiago Gimenez", 29, "Mexico", 18, 4, file.toPath(), "Feyenoord", "Aanvaller");
    }
}
