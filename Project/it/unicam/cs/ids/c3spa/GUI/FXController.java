package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.sql.SQLException;

public interface FXController {

    /**
     * Inizializzare un account
     *
     * @param account account da settare
     * @throws SQLException
     */
    void initData(Account account) throws SQLException;

}
