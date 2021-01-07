package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.util.List;

public class Corriere extends Account {

	private List<Pacco> pacchiInConsegna;
	public int id;

	public Corriere(int corriereId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id = corriereId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password = password;
	}

	public void prendiPacco(Pacco pacco) {
		try {
			pacco.setCorriere(this);
			this.pacchiInConsegna.add(pacco);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void consegnaPacco(Pacco pacco) {
		try {
			pacco.setConsegnato();
			this.pacchiInConsegna.remove(pacco);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}