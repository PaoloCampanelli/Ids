package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IGestiscoAccount;

import java.util.List;

public class Trasportatori implements IGestiscoAccount {
	public List<Corriere> corrieri;
	int numeroCorrieri;


	@Override
	public Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		if(password.length()<5){
			throw new IllegalArgumentException("La password deve contenere almeno 6 caratteri");
		}
		Corriere accountCorriere= new Corriere(id, denominazione, indirizzo, telefono, eMail, password);
		accountCorriere.id= numeroCorrieri;
		numeroCorrieri++;
		corrieri.add(accountCorriere);
		return accountCorriere;
	}

	@Override
	public void SetPassword(String password) {

	}
}