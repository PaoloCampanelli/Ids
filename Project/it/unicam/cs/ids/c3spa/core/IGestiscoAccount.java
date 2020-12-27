package it.unicam.cs.ids.c3spa.core;

public interface IGestiscoAccount {
    Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password);
    void SetPassword(String password);
}
