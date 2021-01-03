package it.unicam.cs.ids.c3spa.core;

import java.util.Date;

public class Pubblicita {
	private int idPubblicita;
	private Date dataInizio;
	private Date dataFine;
	private Negozio negozio;

	public Pubblicita (int idPubblicita, Date dataInizio, Date dataFine, Negozio negozio){
		this.idPubblicita = idPubblicita;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
	}
}