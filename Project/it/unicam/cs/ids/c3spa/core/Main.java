package it.unicam.cs.ids.c3spa.core;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {


        ElencoIndirizzi ei = new ElencoIndirizzi();
        System.out.println(Servizi.caricamento());
        System.out.println(ei.elencoIndirizzi().toString());
        System.out.println(ei.elencoIndirizzi().stream().findFirst().orElse(null).toString());
        //System.out.println(Indirizzo.eliminaIndirizzo(Indirizzo.elencoIndirizzi().stream().findFirst().orElse(null)));
        ei.CreaIndirizzo("der colosseo",10,"Roma","00133","RM");
        System.out.println(ei.elencoIndirizzi().toString());
        System.out.println(ei.eliminaIndirizzo("q", 10,"12345"));
        System.out.println(ei.elencoIndirizzi().toString());




    }
}