package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.sql.SQLException;

public interface FXTabella extends FXController {


    /**
     * Inizializza i dati dell'account
     *
     * @param account   account loggato
     * @param citta     citta inserita
     * @param categoria categoria ricercata
     * @throws SQLException
     */
    void initData(Account account, String citta, String categoria) throws SQLException;


}
