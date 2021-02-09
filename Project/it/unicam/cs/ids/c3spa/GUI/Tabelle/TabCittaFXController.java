package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class TabCittaFXController implements FXController {

    private Cliente cliente;
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
    private TableColumn<Negozio, String> tbCategoria;
    public TabCittaFXController() {
    }

    /**
     * Setta un ObservableList di negozio, filtrata per citta
     * @param cliente
     *          cliente passato
     * @throws SQLException
     */
    public void setta(Cliente cliente) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        GestorePubblicita gp = new GestorePubblicita();
        List<Negozio> negozi = gp.OrderByPubblicita(gn.getByIndirizzo("`indirizzo.citta`", cliente.indirizzo.citta),  gp.getNegoziConPubblicitaAttivaByString("`indirizzo.citta`", cliente.indirizzo.citta));

        ln = FXCollections.observableArrayList(negozi);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
        tbCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categorie.toString()));
        tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
        tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaCitta.setItems(ln);
        tabellaCitta.setPlaceholder(new Label("C3 non contiene negozi a " + cliente.indirizzo.citta));
    }

    /**
     * Inizializza i dati dell'account
     * @param account account loggato
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        setta(getCliente());
        lblCitta.setText(getCliente().indirizzo.citta);
    }


    /**
     *
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Setta l'account se e' un cliente
     * @param account
     */
    public void setCliente(Account account){
        if (account instanceof Cliente) {
            this.cliente = (Cliente) account;
        }
    }


}
