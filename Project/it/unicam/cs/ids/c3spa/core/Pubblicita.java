package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.IMapData;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Pubblicita implements IMapData {
	public int id;
	public Date dataInizio;
	public Date dataFine;
	public Negozio negozio;

	public Pubblicita (int idPubblicita, Date dataInizio, Date dataFine, Negozio negozio){
		this.id = idPubblicita;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
	}

	public Pubblicita() {
		this.negozio=new Negozio();
	}

	public Pubblicita (Date dataInizio, Date dataFine, Negozio negozio){
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
	}

	@Override
	public Object mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("pubblicitaId");
		this.dataInizio = rs.getDate("dataInizio");
		this.dataFine = rs.getDate("dataFine");
		this.negozio = new GestoreNegozio().getById(rs.getInt("negozioId"));
		return this;
	}

	public static int tokenNecessari(Date dataInizio, Date dataFine){

		double diff = dataFine.getTime()-dataInizio.getTime();
		diff = diff /86000000;
		return  (int) diff;
	}
	@Override
	public String toString() {
		return "Pubblicita{" +
				"id=" + id +
				", dataInizio=" + dataInizio +
				", dataFine=" + dataFine +
				", negozio=" + negozio +
				'}';
	}
}