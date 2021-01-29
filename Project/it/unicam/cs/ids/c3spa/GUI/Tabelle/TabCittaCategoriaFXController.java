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

public class TabCittaCategoriaFXController implements FXTabella {
	
	public TabCittaCategoriaFXController() {
	}

	private Cliente cliente;

	private ObservableList<Negozio> ln;

	@FXML
	private Label lblCitta, lblCategoria;
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
	
	public void setta(Cliente cliente, String citta, String categoria) throws SQLException {
		List<Negozio> negozi = new GestoreNegozio().getByCategoriaAndCitta(categoria, citta);
	    ln = FXCollections.observableArrayList(negozi);
	    tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
	    tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
	    tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.via));
	    tbCivico.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.numero));
	    tabellaCitta.setItems(ln);
	    tabellaCitta.setPlaceholder(new Label("C3 non contiene "+categoria+" a "+citta));
	}
	
	@Override
	public void initData(Account account) throws SQLException {}
	
	public void initData(Account account, String citta, String categoria) throws SQLException {
		setta(cliente, citta, categoria);
		lblCategoria.setText(categoria);
		lblCitta.setText(citta);
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
