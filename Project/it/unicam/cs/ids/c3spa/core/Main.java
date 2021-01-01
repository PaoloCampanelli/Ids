package it.unicam.cs.ids.c3spa.core;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {


        System.out.println(Servizi.caricamento());
        System.out.println(Indirizzo.elencoIndirizzi());
        System.out.println(Indirizzo.elencoIndirizzi().stream().findFirst().orElse(null).toString());


    }
}