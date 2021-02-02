package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Cliente;
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

public class TabCategoriaFXController implements FXTabella {

	private Cliente cliente;
	
	public TabCategoriaFXController() {
	}
	
	private ObservableList<Negozio> ln;

	@FXML
	private Label lblCategoria;
	@FXML 
	private TableView<Negozio> tabellaCategoria;
	@FXML
	private TableColumn<Negozio, String> tbNome;
	@FXML
	private TableColumn<Negozio, String> tbNumero;
	@FXML
	private TableColumn<Negozio, String> tbCitta;
	@FXML
	private TableColumn<Negozio, String> tbVia;
	@FXML
	private TableColumn<Negozio, String> tbCivico;
	
	public void setta(String categoria) throws SQLException {
		List<Negozio> negozi = new GestoreNegozio().getByCategoria(categoria);
	    ln = FXCollections.observableArrayList(negozi);
	    tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
	    tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
	    tbCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.citta));
	    tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.via));
	    tbCivico.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.numero));
	    tabellaCategoria.setItems(ln);
	    tabellaCategoria.setPlaceholder(new Label("C3 non contiene questa categoria!"));
	}
	
	
	
	@Override
	public void initData(Account account) throws SQLException
	{
		
	}
	
	@Override
	public void initData(Account account, String citta, String categoria) throws SQLException {
		lblCategoria.setText(categoria);
		setta(categoria);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	

}
