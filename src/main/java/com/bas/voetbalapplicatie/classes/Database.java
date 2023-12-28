package com.bas.voetbalapplicatie.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection conn;

    public Database(){
        try {
            //Maak verbinding met de Database d.m.v. een url, user en wachtwoord
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost/voetbalApplicatie" , "root" , "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
