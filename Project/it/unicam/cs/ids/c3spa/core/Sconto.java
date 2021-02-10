package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.IMapData;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sconto implements IMapData {
	public int id;
	public String tipo;
	public Date dataInizio;
	public Date dataFine;
	public Negozio negozio;
	public CategoriaMerceologica categoriaMerceologica;

	public Sconto (String tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		this.id = 0;
		this.tipo = tipo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
		this.categoriaMerceologica = categoriaMerceologica;
	}

	public Sconto (int scontoId, String tipo, Date dataInizio, Date dataFine, Negozio negozio, CategoriaMerceologica categoriaMerceologica){
		this.id = scontoId;
		this.tipo = tipo;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.negozio = negozio;
		this.categoriaMerceologica = categoriaMerceologica;
	}


	public Sconto() {
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
	
	public List<Sconto> controlloSconti(List<Sconto> ls){

		java.sql.Date oggi = Servizi.dataUtilToSql(Date.from(Instant.now()));
		List<Sconto> attivi = new ArrayList<>();

		for (Sconto sconto: ls)
		{
			if(oggi.after(sconto.dataInizio) && oggi.before(sconto.dataFine))
			{
				attivi.add(sconto);
			}
		}
		return attivi;
	}

	@Override
	public String toString() {
		return "Sconto{" +
				"id=" + id +
				", tipo='" + tipo + '\'' +
				", dataInizio=" + dataInizio +
				", dataFine=" + dataFine +
				", negozio=" + negozio +
				", categoriaMerceologica=" + categoriaMerceologica +
				'}';
	}
}