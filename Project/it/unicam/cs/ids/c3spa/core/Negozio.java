package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Negozio extends Account implements IMapData {
	public int token;
	public List<CategoriaMerceologica> categorie;
	public List<Sconto> sconti;

	public Negozio(int negozioId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id= negozioId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password= password;
		this.token = 5; //TOKEN INIZIALI
		this.categorie = new ArrayList<>();
		this.sconti = new ArrayList<>();
	}

	public Negozio(String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id= 0;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password= password;
		this.token = 5; //TOKEN INIZIALI
		this.categorie = new ArrayList<>();
		this.sconti = new ArrayList<>();
	}

	public Negozio() {
		this.token = 5;
		this.indirizzo = new Indirizzo();
		this.categorie = new ArrayList<>();
		this.sconti = new ArrayList<>();
	}

	@Override
	public Negozio mapData(ResultSet rs) throws SQLException {
		this.id = rs.getInt("negozioId");
		this.denominazione = rs.getString("denominazione");
		this.token = rs.getInt("token");
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

	public boolean aggiungiCategoria(CategoriaMerceologica categoriaMerceologica) {
		//verifico che la categoria che voglio aggiungere non sia già presente, se è gia presente la ritorno.
		if (categorie.stream().anyMatch(c->c.idCategoria==categoriaMerceologica.idCategoria )) {
			return false;
		}
		if (categorie.stream().anyMatch(c->c.nome.equals(categoriaMerceologica.nome))){
			return false;
		}else{
			categorie.add(categoriaMerceologica);
			return true;
		}
	}

	public boolean rimuoviCategoria(CategoriaMerceologica cat){
		if(categorie.contains(cat))
			return categorie.remove(cat);
		return false;
	}

	public Sconto aggiungiSconto(Sconto sconto) {
		//verifico che lo sconto che voglio aggiungere non sia già presente, se è gia presente lo ritorno.
		if (sconti.stream().anyMatch(s->s.id==sconto.id )) {
			return sconto;
		}
		sconti.add(sconto);
		return sconto;
	}

	public Pubblicita aggiungiPubblicita(Pubblicita pubblicita){
		if(pubblicita.negozio.token <= 0){
			throw new IllegalArgumentException("Per usufruire della pubblicità bisogna possedere almeno un token, contatti un amministratore");
		}
		double diff = pubblicita.dataFine.getTime()-pubblicita.dataInizio.getTime();
		diff = diff /86000000;
		int tok = Pubblicita.tokenNecessari(pubblicita.dataInizio, pubblicita.dataFine);
		if(pubblicita.negozio.token-tok >= 0){
			pubblicita.negozio.token= token-tok;
			return pubblicita;
		}
		else{
			throw new IllegalArgumentException("non ha abbastanza token per questo lasso di tempo");
		}


	}

	public List<CategoriaMerceologica> getCategorie() {
		return categorie;
	}

	@Override
	public String toString() {
		return "Negozio{" +
				"token=" + token +
				", categorie=" + categorie +
				", id=" + id +
				", denominazione='" + denominazione + '\'' +
				", indirizzo=" + indirizzo +
				", telefono='" + telefono + '\'' +
				", eMail='" + eMail + '\'' +
				", password='" + password + '\'' +
				'}';
	}


}