package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IGestiscoAccount;

import java.util.List;

public class Cliente extends Account implements IGestiscoAccount
{
    public List<Pacco> pacchi;


    public Cliente(int clienteId, String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password) {
        this.id= clienteId;
        this.denominazione = nomeCognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password = password;
    }
}