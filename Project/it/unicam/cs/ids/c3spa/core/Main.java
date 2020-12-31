package it.unicam.cs.ids.c3spa.core;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Servizi ser = new Servizi();
        System.out.println(ser.caricamento());
        System.out.println("145");
        
    }
}