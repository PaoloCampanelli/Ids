package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IGestiscoAccount;
import it.unicam.cs.ids.c3spa.core.astratto.TipoScontoEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CentroCommerciale implements IGestiscoAccount {
	private List<Negozio> negozi;
	private List<Sconto> sconti;
	private List<Pubblicita> sponsorizzati;
	private int numeroNegozi;

	public CentroCommerciale(){
		this.negozi = new ArrayList<>();
		this.sconti = new ArrayList<>();
		this.sponsorizzati = new ArrayList<>();
	}
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

	public Sconto creaSconto(int id, TipoScontoEnum tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		Sconto sconto = new Sconto(id, tipo, dataInizio, dataFine, negozio, categoriaMerceologica);
		sconti.add(sconto);
		return sconto;
	}

	public void rimuoviSconto(int id, TipoScontoEnum tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		Sconto sconto = new Sconto(id, tipo, dataInizio, dataFine, negozio, categoriaMerceologica);
		sconti.remove(sconto);
	}

	public void rimuoviPubblicita(int idPubblicita, Date dataInizio, Date dataFine, Negozio negozio){
		Pubblicita pubblicita = new Pubblicita(idPubblicita, dataInizio, dataFine, negozio);
		sponsorizzati.remove(pubblicita);
	}

	@Override
	public void SetPassword(String password) {
	}

	public List<Negozio> getNegozi() {
		return negozi;
	}

	public List<Pubblicita> getSponsorizzati() {
		return sponsorizzati;
	}

	public List<Sconto> getSconti(){ return sconti;}

}