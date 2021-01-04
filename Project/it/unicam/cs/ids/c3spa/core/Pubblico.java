package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IGestiscoAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pubblico implements IGestiscoAccount{
	public List<Cliente> clienti;
	public int numeroClienti;

	public Pubblico(){
		this.clienti = new ArrayList<>();
	}

	@Override
	public Account CreaAccount(int id, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) throws SQLException {
		if(password.length()<5){
			throw new IllegalArgumentException("La password deve contenere almeno 6 caratteri");
		}
		Cliente accountCliente= new Cliente(id, denominazione, indirizzo, telefono, eMail, password);
		accountCliente.id= numeroClienti;
		numeroClienti++;
		clienti.add(accountCliente);
		return accountCliente;
	}

	@Override
	public void SetPassword(String password) {

	}
}