package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.IMapData;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Pubblicita implements IMapData {
	private int id;
	private Date dataInizio;
	private Date dataFine;
	private Negozio negozio;

	public Pubblicita (int idPubblicita, Date dataInizio, Date dataFine, Negozio negozio){
		this.id = idPubblicita;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
	}

	public Pubblicita() {
		this.negozio=new Negozio();
	}

	@Override
	public Object mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("pubblicitaId");
		this.dataInizio = rs.getDate("dataInizio");
		this.dataFine = rs.getDate("dataFine");
		this.negozio = new GestoreNegozio().getById(rs.getInt("negozioId"));
		return this;
	}
}