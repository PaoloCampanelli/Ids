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

public class TabCittaCategoriaFXController implements FXStage {

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
    public TabCittaCategoriaFXController() {
    }

    /**
     * Setta un ObservableList di negozi, filtrati per citta e categoria
     * @param citta citta ricercata
     * @param categoria categoria ricercata
     * @throws SQLException
     */
    public void setta(String citta, String categoria) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        GestorePubblicita gp = new GestorePubblicita();
        List<Negozio> negozi = gp.OrderByPubblicita(gn.getByCategoriaAndCitta(categoria, citta), gp.getNegoziConPubblicitaAttivaByCategoriaAndString(categoria, "`indirizzo.citta`", citta));
        ln = FXCollections.observableArrayList(negozi);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
        tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
        tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaCitta.setItems(ln);
        tabellaCitta.setPlaceholder(new Label("C3 non contiene " + categoria + " a " + citta));
    }

    @Override
    public void initData(Account account) throws SQLException {
    }

    /**
     * Inizializza i dati dell'account
     * @param citta     citta inserita
     * @param categoria categoria ricercata
     * @throws SQLException
     */
    public void initData(String citta, String categoria) throws SQLException {
        setta(citta, categoria);
        lblCategoria.setText(categoria);
        lblCitta.setText(citta);
    }

    /**

     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Setta un cliente
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
