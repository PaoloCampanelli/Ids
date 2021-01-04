package it.unicam.cs.ids.c3spa.core.astratto;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.sql.SQLException;

public interface IGestiscoAccount {
    Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) throws SQLException;
    void SetPassword(String password);
}
