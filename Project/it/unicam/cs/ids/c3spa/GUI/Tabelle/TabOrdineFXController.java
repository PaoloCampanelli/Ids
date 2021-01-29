package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import com.mysql.cj.xdevapi.Client;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class TabOrdineFXController implements FXTabella {

    private ObservableList<Pacco> ln;

    @FXML
    private TableView<Pacco> tabellaOrdini;
    @FXML
    private TableColumn<Pacco, String> tbId;
    @FXML
    private TableColumn<Pacco, String> tbMittente;
    @FXML
    private TableColumn<Pacco, String> tbIndirizzo;
    @FXML
    private TableColumn<Pacco, String> tbConsegna;
    @FXML
    private TableColumn<Pacco, String> tbStato;

    private Cliente cliente;


    public void settaTabella(Cliente cliente) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getByDestinatario(cliente.denominazione);
        ln = FXCollections.observableArrayList(pacchi);
        ln.removeIf(p -> p.statiPacco.size()==3);
        tbId.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbMittente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
        tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tbConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
        tbStato.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().statiPacco.size())));
        tabellaOrdini.setItems(ln);
        tabellaOrdini.setPlaceholder(new Label("Non hai effettuato ordini"));
    }


    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        settaTabella(cliente);
    }


    public void setCliente(Account account) throws SQLException {
        if (account instanceof Cliente) {
            Cliente cliente = (Cliente) account;
            this.cliente = cliente;
        }
    }

    @Override
    public void initData(Account account, String citta, String categoria) throws SQLException
    {
    }


}
