package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TabStoricoFXController implements FXTabella {

	private Cliente cliente;
	private Negozio negozio;
	private Corriere corriere;
	
	public TabStoricoFXController() {
	}
	
	private ObservableList<Pacco> storico;

	@FXML 
	private TableView<Pacco> tabellaStorico;
	@FXML
	private TableColumn<Pacco, String> tbMittente;
	@FXML
	private TableColumn<Pacco, String> tbDestinatario;
	@FXML
	private TableColumn<Pacco, String> tbCorriere;
	@FXML
	private TableColumn<Pacco, String> tbIndirizzo;
	@FXML
	private TableColumn<Pacco, String> tbConsegnato;
	

	private void settaCliente(Cliente cliente) throws SQLException {
		List<Pacco> pacchi = new GestorePacco().storicoByCliente(cliente);
		storico = FXCollections.observableArrayList(pacchi);
		settaTabella(tabellaStorico);
		tabellaStorico.setItems(storico);
	}

	private void settaCorriere(Corriere corriere) throws SQLException {
		List<Pacco> pacchi = new GestorePacco().storicoByCorriere(corriere);
		storico = FXCollections.observableArrayList(pacchi);
		settaTabella(tabellaStorico);
		tabellaStorico.setItems(storico);
	}
	
	private void settaNegozio(Negozio negozio) throws SQLException {
		List<Pacco> pacchi = new GestorePacco().storicoByNegozio(negozio);
		storico = FXCollections.observableArrayList(pacchi);
		settaTabella(tabellaStorico);
		tabellaStorico.setItems(storico);	
	}
	
	private void settaTabella(TableView<Pacco> tabellaStorico) {
		tbMittente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
		tbDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.denominazione));
		tbCorriere.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().corriere.denominazione));
		tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
		tbConsegnato.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));	
		tabellaStorico.setPlaceholder(new Label("Nessun ordine presente!"));
	}
	
	
	@Override
	public void initData(Account account) throws SQLException {
		setAccount(account);
		tipo(account);
	}
	
	private void tipo(Account account) throws SQLException {
		if(negozio != null) {
			settaNegozio(negozio);
		}else if(cliente != null) {
			settaCliente(cliente);
		}else if (corriere != null) {
			settaCorriere(corriere);
		}
	}
	
	@Override
	public void initData(Account account, String citta, String categoria) throws SQLException {}
	
	private void setAccount(Account account) {
		if (account instanceof Negozio) {
			Negozio negozio = (Negozio) account;
			this.negozio = negozio;
		}else if( account instanceof Cliente) {
			Cliente cliente = (Cliente) account;
			this.cliente = cliente;
		}else if( account instanceof Corriere) {
			Corriere corriere = (Corriere) account;
			this.corriere = corriere;
		}
	}
	
	
	

}
