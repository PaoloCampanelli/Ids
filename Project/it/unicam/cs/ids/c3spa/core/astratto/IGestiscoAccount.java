package it.unicam.cs.ids.c3spa.core.astratto;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.astratto.Account;

public interface IGestiscoAccount {
    Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password);
    void SetPassword(String password);
}
