package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.TipoScontoEnum;

import java.util.Date;

public class Sconto {
	private int id;
	private TipoScontoEnum tipo;
	private Date dataInizio;
	private Date dataFine;
	private Negozio negozio;
	private CategoriaMerceologica categoriaMerceologica;

	public Sconto (int id, TipoScontoEnum tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		this.id = id;
		this.tipo = tipo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
		this.categoriaMerceologica = categoriaMerceologica;
	}

	public double calcolaScontoPercentuale(double importo, double percentuale){
		return importo = importo * (1 - percentuale/100);
	}
}