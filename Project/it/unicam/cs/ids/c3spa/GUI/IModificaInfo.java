package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.SQLException;

public class IModificaInfo implements FXStage {

    private Cliente cliente;
    private Negozio negozio;
    private Corriere corriere;
    private Amministratore amministatore;
    @FXML
    private TextField txtNome, txtNumero, txtCitta, txtProv, txtCivico, txtVia, txtCap;
    @FXML
    private Button btnConferma;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ChoiceBox<String> chbxProvincia;

    private ObservableList<String> provincia = FXCollections.observableArrayList(
            "AG", "AL","AN","AO", "AQ", "AR", "AP", "AT","AV","BA","BT", "BL", "BN", "BG","BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ", "CH", "CO", "CS", "CR", "KR", "CN", "EN", "FM", "FE", "FI",
            "FG", "FC", "FR", "GE", "GO", "GR", "IM", "IS", "SP", "LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT",  "ME", "MI", "MO", "MB", "NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE",
            "PC", "PI", "PT", "PN", "PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", "SO", "TA", "TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC","VS", "VR", "VV", "VI", "VT");


    public IModificaInfo() {
    }

    public void actionConferma() throws Exception {
        if (negozio != null) {
            if(accettaModifiche(negozio) ==1){
                new GestoreNegozio().cambiaPassword(negozio);
            }
            new GestoreNegozio().save(negozio);
        } else if (cliente != null) {
            if(accettaModifiche(cliente) == 1){
                new GestoreCliente().cambiaPassword(cliente);
            }
            new GestoreCliente().save(cliente);
        } else if (corriere != null) {
            if(accettaModifiche(corriere) == 1){
                new GestoreCorriere().cambiaPassword(corriere);
            }
            new GestoreCorriere().save(corriere);
        }
        alert();
        Stage attuale = (Stage) btnConferma.getScene().getWindow();
        attuale.close();
    }

    public void actionPulisci() {
        txtNome.clear();
        txtNumero.clear();
        txtCitta.clear();
        txtCap.clear();
        txtCivico.clear();
        txtVia.clear();
        txtProv.clear();
        txtPassword.clear();
    }

    private void alert() {
        Alert alert = new Alert(AlertType.INFORMATION, "Effettua il login!");
        alert.setTitle("Modifica dati");
        alert.showAndWait();
    }

    /**
     * Inizializza i dati dell'account
     *
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setAccount(account);
        settaCampo(account);
        chbxProvincia.setItems(provincia);
        chbxProvincia.setValue(account.indirizzo.provincia);
    }

    /**
     *
     * Riceve le determinate modifiche dell'utente e le setta
     *
     * @param account account da modificare
     */
    private int accettaModifiche(Account account){
        int i = 0;
        if (!txtNome.getText().isBlank()) {
            account.denominazione = txtNome.getText().toUpperCase();
        }
        if (!txtPassword.getText().isBlank() && (txtPassword.getText().length() > 6)) {
            account.password = txtPassword.getText();
            i++;
        }
        if (!txtNumero.getText().isBlank() && txtNumero.getText().length() == 10) {
            account.telefono = txtNumero.getText().toUpperCase();
        }
        if (!txtCitta.getText().isBlank()) {
            account.indirizzo.citta = txtCitta.getText().toUpperCase();
        }
        if ((!txtCap.getText().isBlank()) && txtCap.getText().length() == 5) {
            account.indirizzo.cap = txtCap.getText().toUpperCase();
        }
        if ((!txtCivico.getText().isBlank())) {
            account.indirizzo.numero = txtCivico.getText().toUpperCase();
        }
        if ((!txtVia.getText().isBlank())) {
            account.indirizzo.via = txtVia.getText().toUpperCase();
        }
        account.indirizzo.provincia = chbxProvincia.getValue();
        return i;
    }

    /**
     * Setta i campi dell'account
     *
     * @param account account da modificare
     */
    private void settaCampo(Account account) {
        txtNome.setPromptText(account.denominazione);
        txtNumero.setPromptText(account.telefono);
        txtCitta.setPromptText(account.indirizzo.citta);
        txtCap.setPromptText(account.indirizzo.cap);
        txtCivico.setPromptText(account.indirizzo.numero);
        txtVia.setPromptText(account.indirizzo.via);
    }

    /**
     * Setta l'account in base alla sua istanza
     *
     * @param account account da settare
     */
    private void setAccount(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        } else if (account instanceof Cliente) {
            this.cliente = (Cliente) account;
        } else if (account instanceof Corriere) {
            this.corriere = (Corriere) account;
        }else if (account instanceof Amministratore){
            this.amministatore = (Amministratore) account;
        }
    }

}
