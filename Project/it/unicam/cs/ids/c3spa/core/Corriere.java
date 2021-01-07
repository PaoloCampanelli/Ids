package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Corriere extends Account implements IMapData {

	public List<Pacco> pacchiInConsegna;
	public String denominazione;

	public Corriere(int corriereId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id = corriereId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password = password;
	}

	public Corriere(int corriereId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password, List<Pacco> pacchiInConsegna) {
		this.id = corriereId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password = password;
		this.pacchiInConsegna = pacchiInConsegna;
	}

	public Corriere() {
		this.indirizzo = new Indirizzo();
		this.pacchiInConsegna = new ArrayList<Pacco>();
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

	@Override
	public Corriere mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("corriereId");
		this.denominazione = rs.getString("denominazione");
		this.indirizzo.citta = rs.getString("indirizzo.citta");
		this.indirizzo.numero = rs.getString("indirizzo.numero");
		this.indirizzo.cap = rs.getString("indirizzo.cap");
		this.indirizzo.via = rs.getString("indirizzo.via");
		this.indirizzo.provincia = rs.getString("indirizzo.provincia");
		this.telefono = rs.getString("telefono");
		this.eMail = rs.getString("eMail");
		this.password = rs.getString("password");
		return this;
	}

	@Override
	public String toString() {
		return "Corriere{" +
				"pacchiInConsegna=" + pacchiInConsegna +
				", id=" + id +
				", denominazione='" + denominazione + '\'' +
				", indirizzo=" + indirizzo +
				", telefono='" + telefono + '\'' +
				", eMail='" + eMail + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}