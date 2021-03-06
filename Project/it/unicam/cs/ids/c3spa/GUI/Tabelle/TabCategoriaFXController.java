package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXStage;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class TabCategoriaFXController implements FXStage {

    private Cliente cliente;
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


    public TabCategoriaFXController() {
    }

	/**
	 * Setta un ObservableList di categoria
	 * @param categoria
	 * 			categoria ricercata
	 * @throws SQLException
	 */
	public void setta(String categoria) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        GestorePubblicita gp = new GestorePubblicita();
        List<Negozio> negozi = gp.OrderByPubblicita(gn.getByCategoria(categoria), gp.getNegoziConPubblicitaAttivaByCategoria(categoria));
        ln = FXCollections.observableArrayList(negozi);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
        tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
        tbCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaCategoria.setItems(ln);
        tabellaCategoria.setPlaceholder(new Label("C3 non contiene questa categoria!"));
    }


    @Override
    public void initData(Account account) throws SQLException {
    }

	/**
	 * Inizializza i dati della ricerca
	 * @param categoria categoria ricercata
	 * @throws SQLException
	 */

    public void initData(String categoria) throws SQLException {
        lblCategoria.setText(categoria);
        setta(categoria);
    }

	/**
	 *
	 * @return cliente
 	 */
	public Cliente getCliente() {
        return cliente;
    }

	/**
	 * Setta il cliente
	 * @param cliente
	 */
	public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}
