package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IGestiscoAccount;

import java.util.List;

public class CentroCommerciale implements IGestiscoAccount {
	public List<Negozio> negozi;
	public Sconto sconti;
	public Pubblicita sponsorizzati;
	public int numeroNegozi;

	@Override
	public Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		if(password.length()<5){
			throw new IllegalArgumentException("La password deve contenere almeno 6 caratteri");
		}
		Negozio accountNegozio= new Negozio(id, denominazione, indirizzo, telefono, eMail, password);
		accountNegozio.id= numeroNegozi;
		numeroNegozi++;
		negozi.add(accountNegozio);
		return accountNegozio;
	}

	@Override
	public void SetPassword(String password) {

	}
}