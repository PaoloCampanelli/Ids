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

public class TabCittaFXController implements FXTabella{

	private Cliente cliente;
	
	
	public TabCittaFXController() {
	}
	
	private ObservableList<Negozio> ln;

	@FXML
	private Label lblCitta;
	@FXML 
	private TableView<Negozio> tabellaCitta;
	@FXML
	private TableColumn<Negozio, String> tbNome;
	@FXML
	private TableColumn<Negozio, String> tbNumero;
	@FXML
	private TableColumn<Negozio, String> tbVia;
	@FXML
	private TableColumn<Negozio, String> tbCivico;
	@FXML
	private TableColumn<Negozio, String> tbCategoria;
	
	
	public void setta(Cliente cliente) throws SQLException {
		List<Negozio> negozi = new GestoreNegozio().getByIndirizzo("indirizzo.citta", cliente.indirizzo.citta);
	    ln = FXCollections.observableArrayList(negozi);
	    tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
	    tbCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categorie.toString()));
	    tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
	    tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.via));
	    tbCivico.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.numero));
	    tabellaCitta.setItems(ln);
	    tabellaCitta.setPlaceholder(new Label("C3 non contiene negozi a "+cliente.indirizzo.citta));
	}

	@Override
	public void initData(Account account) throws SQLException {
		lblCitta.setText(getCliente().indirizzo.citta);
		setta(getCliente());
	}

	@Override
	public void initData(Account account, String citta, String categoria) throws SQLException{
		setCliente(account);
		lblCitta.setText(account.indirizzo.citta);
		setta(getCliente());
	}
	

	public void setCliente(Account account) throws SQLException {
		if (account instanceof Cliente) {
			Cliente cliente = (Cliente) account;
			this.cliente = cliente;
		}
	}
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		
	}
	

}
