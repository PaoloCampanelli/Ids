package it.unicam.cs.ids.c3spa.GUI;


import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class RegistrazioneFXController implements FXStage {

    private ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("CLIENTE", "CORRIERE", "NEGOZIO");
    private ObservableList<String> provincia = FXCollections.observableArrayList(
            "AG", "AL","AN","AO", "AQ", "AR", "AP", "AT","AV","BA","BT", "BL", "BN", "BG","BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ", "CH", "CO", "CS", "CR", "KR", "CN", "EN", "FM", "FE", "FI",
            "FG", "FC", "FR", "GE", "GO", "GR", "IM", "IS", "SP", "LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT",  "ME", "MI", "MO", "MB", "NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE",
            "PC", "PI", "PT", "PN", "PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", "SO", "TA", "TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC","VS", "VR", "VV", "VI", "VT");

    @FXML
    private TextField txtNome, txtNumero, txtCitta, txtCivico, txtVia, txtCap, txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblErrore;
    @FXML
    private ChoiceBox<String> tipologia;
    @FXML
    private ChoiceBox<String> chbxProvincia;
    @FXML
    private Button btnRegistrati;

    public RegistrazioneFXController() {
    }

    @FXML
    public void initialize() {
        tipologia.setItems(tipologiaDisponibile);
        tipologia.setValue("CLIENTE");
        chbxProvincia.setItems(provincia);
        chbxProvincia.setValue("AG");
    }

    public void actionRegistrazione() throws Exception {
        registrazione();
    }

    public void actionPulisci() {
        txtNome.clear();
        txtNumero.clear();
        txtCitta.clear();
        txtCap.clear();
        txtCivico.clear();
        txtVia.clear();

        txtPassword.clear();
    }

    /**
     * Registra l'account in base alla sua tipologia.
     * Costruisce l'account tramite i parametri in input. Effettua dei controlli e rimanda alla view corrispondente.
     *
     * @throws SQLException
     */
    private void registrazione() throws SQLException {
        lblErrore.setText(" ");
        String email = txtEmail.getText().toUpperCase();
        String nome = txtNome.getText().toUpperCase();
        String passw = txtPassword.getText();
        String numero = txtNumero.getText().toUpperCase();
        String via = txtVia.getText().toUpperCase();
        String prov = chbxProvincia.getValue();
        String citta = txtCitta.getText().toUpperCase();
        String cap = txtCap.getText().toUpperCase();
        String civico = txtCivico.getText().toUpperCase();
        String tipologia = tipologia();
        if (controllaInfo(tipologia, email, nome, passw, numero)) {
            if (controllaIndirizzo(via, citta, cap, civico, prov)) {
                Indirizzo indirizzo = new Indirizzo(via, civico, citta, cap, prov);
                Stage attuale = (Stage) btnRegistrati.getScene().getWindow();
                attuale.hide();
                switch (tipologia) {
                    case "CLIENTE": {
                        GestoreCliente gc = new GestoreCliente();
                        Cliente nuovo = new Cliente(nome, indirizzo, numero, email, passw);
                        gc.save(nuovo);
                        break;
                    }
                    case "NEGOZIO": {
                        GestoreNegozio gc = new GestoreNegozio();
                        Negozio nuovo = new Negozio(nome, indirizzo, numero, email, passw);
                        gc.save(nuovo);
                        break;
                    }
                    case "CORRIERE": {
                        GestoreCorriere gc = new GestoreCorriere();
                        Corriere nuovo = new Corriere(0, nome, indirizzo, numero, email, passw);
                        gc.save(nuovo);
                        break;
                    }
                }

            } else
                lblErrore.setText("INDIRIZZO ERRATO");
        } else
            lblErrore.setText("INFORMAZIONI PERSONALI NON VALIDE!");
    }

    /**
     * Controlla se i dati dell'indirizzo inserito è corretto
     *
     * @param via    via inserita
     * @param citta  citta inserita
     * @param cap    cap inserito -> 5 caratteri
     * @param civico
     * @return
     */
    private boolean controllaIndirizzo(String via, String citta, String cap, String civico, String provincia){
        if (!citta.isBlank()) {
            if ((!cap.isBlank()) && cap.length() == 5) {
                if ((!civico.isBlank())) {
                    if ((!via.isBlank())) {
                        if(!provincia.isBlank())
                             return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Controlla che le informazioni inserite siano corrette
     *
     * @param tipologia tipologia corrispondente
     * @param email     email inserita
     * @param nome      nome inserito
     * @param passw     password -> almeno 6 caratteri
     * @param numero    numero -> almeno 10 caratteri
     * @return true se i valori inseriti sono corretti
     * @throws SQLException
     */
    private boolean controllaInfo(String tipologia, String email, String nome, String passw, String numero) throws SQLException {
        if (!email.isBlank()) {
            if (email.contains("@")) {
                if (!emailEsistente(email.toUpperCase(), tipologia)) {
                    if (!nome.isBlank()) {
                        if (!passw.isBlank() && (passw.length() > 5)) {
                            return !numero.isBlank() & numero.length() == 10;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return tipologia corrispondente
     */
    private String tipologia() {
        tipologia.getValue();
        switch (tipologia.getValue()) {
            case "NEGOZIO":
                return "NEGOZIO";
            case "CLIENTE":
                return "CLIENTE";
            case "CORRIERE":
                return "CORRIERE";
        }
        return "";
    }

    /**
     * Controlla se la mail inserita corrispondeva ad un determinato account con una tipologia uguale a quella inserita
     *
     * @param email     email inserita
     * @param tipologia tipologia corrispondente
     * @throws SQLException
     * @return true se esiste l'account con tipologia ed email inserita.
     */
    private boolean emailEsistente(String email, String tipologia) throws SQLException {
        switch (tipologia) {
            case "CLIENTE": {
                List<Cliente> lc = new GestoreCliente().getAll();
                return lc.stream().anyMatch(c -> c.eMail.equals(email));
            }
            case "NEGOZIO": {
                List<Negozio> lc = new GestoreNegozio().getAll();
                return lc.stream().anyMatch(c -> c.eMail.equals(email));
            }
            case "CORRIERE": {
                List<Corriere> lc = new GestoreCorriere().getAll();
                return lc.stream().anyMatch(c -> c.eMail.equals(email));
            }
        }
        return false;
    }


    @Override
    public void initData(Account account) throws SQLException {
    }


}

