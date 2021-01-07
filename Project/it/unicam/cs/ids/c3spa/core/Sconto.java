package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.IMapData;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

public class Sconto implements IMapData {
	public int id;
	public String tipo;
	public Date dataInizio;
	public Date dataFine;
	public Negozio negozio;
	public CategoriaMerceologica categoriaMerceologica;

	public Sconto (int id, String tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		this.id = id;
		this.tipo = tipo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
		this.categoriaMerceologica = categoriaMerceologica;
	}

	public Sconto() {
		Date dataAttuale = Date.from(Instant.now());
		this.dataInizio = dataAttuale;
		this.categoriaMerceologica = new CategoriaMerceologica();
		this.negozio = new Negozio();
	}

	@Override
	public Sconto mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("scontoId");
		this.tipo = rs.getString("tipo");
		this.dataInizio = rs.getDate("dataInizio");
		this.dataFine = rs.getDate("dataFine");
		this.negozio = new GestoreNegozio().getById(rs.getInt("negozioId"));
		this.categoriaMerceologica = new GestoreCategoriaMerceologica().getById(rs.getInt("categoriaMerceologicaId"));
		return this;
	}

	public double calcolaScontoPercentuale(double importo, double percentuale){
		return importo = importo * (1 - percentuale/100);
	}
}