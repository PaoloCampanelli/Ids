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
}