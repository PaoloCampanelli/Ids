package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TabClientiFXController implements FXTabella {

	public TabClientiFXController() {
	}
	
	private ObservableList<Cliente> ln;
	
	@FXML 
	private TableView<Cliente> tabellaClienti;
	@FXML
	private TableColumn<Cliente, String> tbNome;
	@FXML
	private TableColumn<Cliente, String> tbNumero;
	@FXML
	private TableColumn<Cliente, String> tbCitta;
	
	@FXML
	public void initialize() throws SQLException {
		List<Cliente> clienti = new GestoreCliente().getAll();
	    ln = FXCollections.observableArrayList(clienti);
	    tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
	    tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
	    tbCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.citta));
	    tabellaClienti.setItems(ln);
	    tabellaClienti.setPlaceholder(new Label("C3 non contiene ancora clienti!"));
	}

	@Override
	public void initData(Account account) throws SQLException 
	{
	}

	@Override
	public void initData(Account account, String citta, String categoria) throws SQLException
	{
	}

}
