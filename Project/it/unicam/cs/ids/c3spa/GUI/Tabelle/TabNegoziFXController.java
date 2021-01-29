package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TabNegoziFXController implements FXTabella {

	private ObservableList<Negozio> ln;
	
	@FXML 
	private TableView<Negozio> tabellaNegozi;
	@FXML
	private TableColumn<Negozio, String> tbNome;
	@FXML
	private TableColumn<Negozio, String> tbNumero;
	@FXML
	private TableColumn<Negozio, String> tbVia;
	@FXML
	private TableColumn<Negozio, String> tbCivico;
	@FXML
	private TableColumn<Negozio, String> tbCitta;
	
	@FXML
	public void initialize() throws SQLException {
		List<Negozio> negozi = new GestoreNegozio().getAll();
	    ln = FXCollections.observableArrayList(negozi);
	    tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
	    tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
	    tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.via));
	    tbCivico.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.numero));
	    tbCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.citta));
	    tabellaNegozi.setItems(ln);
	    tabellaNegozi.setPlaceholder(new Label("C3 non contiene ancora negozi!"));
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
