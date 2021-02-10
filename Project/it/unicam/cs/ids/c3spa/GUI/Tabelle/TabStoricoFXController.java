package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
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

public class TabStoricoFXController implements FXController {

        private Cliente cliente;
        private Negozio negozio;
        private Corriere corriere;
        private ObservableList<Pacco> storico;
        @FXML
        private TableView<Pacco> tabellaStorico;
        @FXML
        private TableColumn<Pacco, String> tbMittente;
        @FXML
        private TableColumn<Pacco, String> tbDestinatario;
        @FXML
        private TableColumn<Pacco, String> tbCorriere;
        @FXML
        private TableColumn<Pacco, String> tbIndirizzo;
        @FXML
        private TableColumn<Pacco, String> tbConsegnato;
        public TabStoricoFXController() {
        }

        /**
         * Setta un ObservableList dello storico del cliente
         * @param cliente
         * @throws SQLException
         */
        private void settaCliente(Cliente cliente) throws SQLException {
            List<Pacco> pacchi = new GestorePacco().storicoByCliente(cliente);
            storico = FXCollections.observableArrayList(pacchi);
            settaTabella(tabellaStorico);
            tabellaStorico.setItems(storico);
        }

        /**
         * Setta un ObservableList dello storico del corriere
         * @param corriere
         * @throws SQLException
         */
        private void settaCorriere(Corriere corriere) throws SQLException {
            List<Pacco> pacchi = new GestorePacco().storicoByCorriere(corriere);
            storico = FXCollections.observableArrayList(pacchi);
            settaTabella(tabellaStorico);
            tabellaStorico.setItems(storico);
        }

        /**
         * Setta un ObservableList dello storico del negozio
         * @param negozio
         * @throws SQLException
         */
        private void settaNegozio(Negozio negozio) throws SQLException {
            List<Pacco> pacchi = new GestorePacco().storicoByNegozio(negozio);
            storico = FXCollections.observableArrayList(pacchi);
            settaTabella(tabellaStorico);
            tabellaStorico.setItems(storico);
        }

        /**
         * Setta un ObservableList del pacco
         * @param tabellaStorico
         */
        private void settaTabella(TableView<Pacco> tabellaStorico) {
            storico.removeIf(i -> i.id == 0);
            tbMittente.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
            tbDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.denominazione));
            tbCorriere.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().corriere.denominazione));
            tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
            tbConsegnato.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
            tabellaStorico.setPlaceholder(new Label("Nessun ordine presente!"));
        }

    /**
     * Inizializza i dati dell'account
     * @param account account loggato
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setAccount(account);
        tipo();
    }

    /**
     * Setta la tabella in base alla tipologia
     * @throws SQLException
     */
    private void tipo() throws SQLException {
        if (negozio != null) {
            settaNegozio(negozio);
        } else if (cliente != null) {
            settaCliente(cliente);
        } else if (corriere != null) {
            settaCorriere(corriere);
        }
    }


    /**
     * Setta l'account in base alla sua istanza
     * @param account
     */
    public void setAccount(Account account) {
        if (account instanceof Negozio) {
            Negozio negozio = (Negozio) account;
            this.negozio = negozio;
        } else if (account instanceof Cliente) {
            Cliente cliente = (Cliente) account;
            this.cliente = cliente;
        } else if (account instanceof Corriere) {
            Corriere corriere = (Corriere) account;
            this.corriere = corriere;
        }
    }

}
