package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.List;

public class Negozio extends Account implements IMapData {
	public int token;
	public List<CategoriaMerceologica> categorie;

	public Negozio(int negozioId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id= negozioId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password= password;
		this.token = 5; //TOKEN INIZIALI
		this.categorie = new ArrayList<CategoriaMerceologica>();
	}

	public Negozio(String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id= 0;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password= password;
		this.token = 5; //TOKEN INIZIALI
		this.categorie = new ArrayList<CategoriaMerceologica>();
	}

	public Negozio() {
		this.indirizzo = new Indirizzo();
		this.categorie = new ArrayList<CategoriaMerceologica>();
	}

	@Override
	public Negozio mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("negozioId");
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

	public CategoriaMerceologica aggiungiCategoria(CategoriaMerceologica categoriaMerceologica) {
		//verifico che la categoria che voglio aggiungere non sia già presente, se è gia presente la ritorno.
		if (categorie.stream().anyMatch(c->c.idCategoria==categoriaMerceologica.idCategoria )) {
			return categoriaMerceologica;
		}
		categorie.add(categoriaMerceologica);
		return categoriaMerceologica;
	}

	public void rimuoviCategoria(CategoriaMerceologica cat){
		if(categorie.contains(cat)){
			categorie.remove(cat);
		}
	}

	public boolean attivaPubblicita(int idPubblicita, Date dataInizio, Date dataFine, Negozio negozio){
		if(token <= 0){
			throw new IllegalArgumentException("Per usufruire della pubblicità bisogna possedere almeno un token");
		}
		new Pubblicita(idPubblicita, dataInizio, dataFine, negozio);
		token--;
		return true;
	}

	public List<CategoriaMerceologica> getCategorie() {
		return categorie;
	}

/*	public List<Pacco> getPacchi() {
		return pacchi;
	}*/

/*	public String getIndirizzo() {
		throw new UnsupportedOperationException();
	}

	public void setIndirizzo(String aIndirizzo) {
		throw new UnsupportedOperationException();
	}*/

	@Override
	public String toString() {
		return "Negozio{" +
				"id=" + id +
				", denominazione='" + denominazione + '\'' +
				", indirizzo=" + indirizzo +
				", telefono='" + telefono + '\'' +
				", eMail='" + eMail + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}