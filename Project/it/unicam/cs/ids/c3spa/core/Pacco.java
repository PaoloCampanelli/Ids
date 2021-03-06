package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.astratto.IMapData;
import it.unicam.cs.ids.c3spa.astratto.StatoPaccoEnum;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

public class Pacco  implements IMapData {
	public int id;
	public Cliente destinatario;
	public Negozio mittente;
	public Corriere corriere;
	public Date dataPreparazione;
	public Date dataConsegnaRichiesta;
	public Indirizzo indirizzo;
	public List<StatoPacco> statiPacco;

	public Pacco CreaPacco(int idPacco, Cliente destinatario, Negozio mittente, Date dataConsegnaRichiesta, Indirizzo indirizzo) {
		Date dataAttuale = Date.from(Instant.now());
		this.id = idPacco;
		this.destinatario = destinatario;
		this.mittente = mittente;
		this.dataPreparazione = dataAttuale;
		this.dataConsegnaRichiesta = dataConsegnaRichiesta;
		this.corriere = null;
		this.indirizzo = indirizzo;
		this.statiPacco = new ArrayList<StatoPacco>();
		this.statiPacco.add(new StatoPacco(StatoPaccoEnum.preparato, dataAttuale));
		return this;
	}

	public Pacco(Cliente destinatario, Negozio mittente, Date dataConsegnaRichiesta, Indirizzo indirizzo){
		Date dataAttuale = Date.from(Instant.now());
		this.id = 0;
		this.destinatario = destinatario;
		this.mittente = mittente;
		this.dataPreparazione = dataAttuale;
		this.dataConsegnaRichiesta = dataConsegnaRichiesta;
		this.corriere = null;
		this.indirizzo = indirizzo;
		this.statiPacco = new ArrayList<StatoPacco>();
		this.statiPacco.add(new StatoPacco(StatoPaccoEnum.preparato, dataAttuale));
	}


	public Pacco(){
		Date dataAttuale = Date.from(Instant.now());
		this.destinatario = new Cliente();
		this.mittente = new Negozio();
		this.dataPreparazione = dataAttuale;
		this.corriere = new Corriere();
		this.indirizzo = this.destinatario.indirizzo;
		this.statiPacco = new ArrayList<StatoPacco>();
	}

	public void  setCorriere(Corriere corriere) throws SQLException {
		//Verifichiamo che il pacco non sia già assegnato ad un corriere
		//b->b.stato.equals(StatoPaccoEnum.assegnato
		if (this.statiPacco.stream().noneMatch(p -> p.stato.equals(StatoPaccoEnum.assegnato))) {
			this.corriere = corriere;
			this.statiPacco.add(new StatoPacco(StatoPaccoEnum.assegnato, Date.from(Instant.now())));
		}
		else
			throw new ConcurrentModificationException("Il pacco è gia stato preso in consegna");
	}

	public void setConsegnato()
	{
		//Controlliamo che il pacco non sia già consegnato
		if (this.statiPacco.stream().noneMatch(p->p.stato.equals(StatoPaccoEnum.consegnato))) {
			this.statiPacco.add(new StatoPacco(StatoPaccoEnum.consegnato, Date.from(Instant.now())));
		}
		else
			throw new ConcurrentModificationException("Il pacco è gia stato consegnato");
	}


	@Override
	public Pacco mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("paccoId");
		this.destinatario = new Cliente();
		this.destinatario.id = rs.getInt("destinatario");
		this.mittente = new Negozio();
		this.mittente.id = rs.getInt("mittente");
		this.dataPreparazione = rs.getDate("dataPreparazione");
		this.dataConsegnaRichiesta = rs.getDate("dataConsegnaRichiesta");
		this.corriere = new Corriere();
		this.corriere.id = rs.getInt("corriere");
		this.indirizzo.citta = rs.getString("indirizzo.citta");
		this.indirizzo.numero = rs.getString("indirizzo.numero");
		this.indirizzo.cap = rs.getString("indirizzo.cap");
		this.indirizzo.via = rs.getString("indirizzo.via");
		this.indirizzo.provincia = rs.getString("indirizzo.provincia");
		return this;
	}

	@Override
	public String toString() {
		return "Pacco{" +
				"id=" + id +
				", destinatario=" + destinatario +
				", mittente=" + mittente +
				", corriere=" + corriere +
				", dataPreparazione=" + dataPreparazione +
				", dataConsegnaRichiesta=" + dataConsegnaRichiesta +
				", indirizzo=" + indirizzo +
				", statiPacco=" + statiPacco +
				'}';
	}
}