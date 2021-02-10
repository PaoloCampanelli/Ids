package it.unicam.cs.ids.c3spa.core.astratto;

import it.unicam.cs.ids.c3spa.core.Indirizzo;

public abstract class Account implements IGestiscoAccount {
    public int id;
    public String denominazione;
    public Indirizzo indirizzo;
    public String telefono;
    public String eMail;
    public String password;

    @Override
    public Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
        this.id = id;
        this.denominazione = denominazione;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password= password;
        return this;
    }

    @Override
    public void SetPassword(String password) {
        this.password = password;
    }

}
