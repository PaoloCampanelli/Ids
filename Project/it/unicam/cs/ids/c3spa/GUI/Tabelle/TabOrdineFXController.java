package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.Cliente;
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

import java.sql.SQLException;
import java.util.List;

public class TabOrdineFXController implements FXController {

    private ObservableList<Pacco> ln;

    @FXML
    private TableView<Pacco> tabellaOrdini;
    @FXML
    private TableColumn<Pacco, String> tbID;
    @FXML
    private TableColumn<Pacco, String> tbMittente;
    @FXML
    private TableColumn<Pacco, String> tbIndirizzo;
    @FXML
    private TableColumn<Pacco, String> tbConsegna;
    @FXML
    private TableColumn<Pacco, String> tbStato;

    private Cliente cliente;

    /**
     * Setta un ObservableList di Clienti
     * @param cliente
     *          cliente passato
     * @throws SQLException
     */
    public void settaTabella(Cliente cliente) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getByDestinatario(cliente);
        ln = FXCollections.observableArrayList(pacchi);
        ln.removeIf(p -> p.statiPacco.size() == 3);
        tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbMittente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
        tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tbConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
        tbStato.setCellValueFactory(cd -> new SimpleStringProperty(prendiPacco(cd.getValue())));
        tabellaOrdini.setItems(ln);
        tabellaOrdini.setPlaceholder(new Label("Non hai effettuato ordini"));
    }

    /**
     * Imposta lo stato di un pacco
     * @param pacco
     *          pacco da inserire
     * @return NON ASSEGNATO -> se lo stato e' 1
     *          PRESO IN CARICO -> se lo stato e' 2
     */
    private String prendiPacco(Pacco pacco) {
        if (pacco.statiPacco.size() == 1) {
            return "NON ASSEGNATO";
        } else if (pacco.statiPacco.size() == 2) {
            return "PRESO IN CARICO";
        }
        return "";

    }

    /**
     * Inizializza i dati dell'account
     * @param account account loggato
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        settaTabella(cliente);
    }

    /**
     * Setta l'account se e' un cliente
     * @param account
     */
    public void setCliente(Account account) {
        if (account instanceof Cliente) {
            this.cliente = (Cliente) account;
        }
    }

}
