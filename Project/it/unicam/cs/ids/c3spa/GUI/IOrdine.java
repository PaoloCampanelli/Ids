package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.controller.ClienteController;
import it.unicam.cs.ids.c3spa.controller.NegozioController;
import it.unicam.cs.ids.c3spa.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IOrdine implements FXStage {

    private Cliente cliente;
    private Negozio negozio;
    private NegozioController controllerNegozio = new NegozioController();
    private ClienteController controllerCliente = new ClienteController();
    private ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("PREDEFINITO", "NUOVO");
    private ObservableList<Pacco> pacchiNonAssegnati;
    private ObservableList<Cliente> clienteDisponibili;
    @FXML
    TableView<Cliente> tabellaCliente;
    @FXML
    TableView<Pacco> tabellaPacchi;
    @FXML
    TableColumn<Cliente, String> tbNome;
    @FXML
    TableColumn<Cliente, String> tbIndirizzo;
    @FXML
    TableColumn<Cliente, String> tbEmail;
    @FXML
    TextField txtEmail, txtId, txtEmailRicerca, txtVia, txtCitta, txtCivico, txtProv, txtCap;
    @FXML
    DatePicker dpData;
    @FXML
    Button btnRicerca, btnAnnulla, btnCrea, btnResetta, btnInserisci1, btnInserisci2;
    @FXML
    HBox hboxNascosta;
    @FXML
    Label lblAnnulla, lblEmail, lblErroreIndirizzo, lblInfoNome, lblData, lblInfoIndirizzo, lblInfoData, lblErrore, lblErrore2;
    @FXML
    TableColumn<Pacco, String> tbId;
    @FXML
    TableColumn<Pacco, String> tbDestinatario;
    @FXML
    TableColumn<Pacco, String> tbIndirizzoPacco;
    @FXML
    TableColumn<Pacco, String> tbDataPacco;
    @FXML
    ChoiceBox<String> selezionaIndirizzo;
    @FXML
    ChoiceBox<String> chbxProvincia;

    private ObservableList<String> provincia = FXCollections.observableArrayList(
            "AG", "AL","AN","AO", "AQ", "AR", "AP", "AT","AV","BA","BT", "BL", "BN", "BG","BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ", "CH", "CO", "CS", "CR", "KR", "CN", "EN", "FM", "FE", "FI",
            "FG", "FC", "FR", "GE", "GO", "GR", "IM", "IS", "SP", "LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT",  "ME", "MI", "MO", "MB", "NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE",
            "PC", "PI", "PT", "PN", "PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", "SO", "TA", "TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC","VS", "VR", "VV", "VI", "VT");


    public IOrdine() {
    }

    @FXML
    public void initialize() {
        selezionaIndirizzo.setItems(tipologiaDisponibile);
        selezionaIndirizzo.setValue("PREDEFINITO");
        hboxNascosta.setDisable(true);
        chbxProvincia.setItems(provincia);
    }

    /**
     * Setta un ObservableList di pacchi del negozio
     *
     * @param negozio negozio da utilizzare
     * @throws SQLException
     */
    public void settaPacchi(Negozio negozio) throws SQLException {
        List<Pacco> pacco = new GestorePacco().getPacchiNonAssegnatiByMittente(negozio);
        pacchiNonAssegnati = FXCollections.observableArrayList(pacco);
        tbId.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
        tbIndirizzoPacco.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tbDataPacco.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
        pacchiNonAssegnati.removeIf(p -> p.id == 0);
        tabellaPacchi.setItems(pacchiNonAssegnati);
        tabellaPacchi.setPlaceholder(new Label(negozio.denominazione + " non contiene pacchi!"));
    }

    /**
     * Setta un ObservableList di clienti
     *
     * @throws SQLException
     */
    public void settaClienti() throws SQLException {
        lblEmail.setText(" ");
        List<Cliente> cliente = new GestoreCliente().getAll();
        clienteDisponibili = FXCollections.observableArrayList(cliente);
        tbEmail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaCliente.setItems(clienteDisponibili);
        tabellaCliente.setPlaceholder(new Label("C3 non contiene clienti!"));
    }

    public void actionInserisciEmail() throws SQLException {
        settaClienti();
        inserisciEmail(txtEmail.getText());
    }

    public void actionInserisciIndirizzo() {
        lblErrore2.setText("");
        if(btnInserisci1.isDisable() == true) {
            lblErrore2.setText("Email mancante");
        } else {
            lblErrore2.setText("");
            lblInfoIndirizzo.setText(" ");
            String via = txtVia.getText();
            String citta = txtCitta.getText();
            String prov = chbxProvincia.getValue();
            String civico = txtCivico.getText();
            String cap = txtCap.getText();
            if (selezionaIndirizzo.getValue().equals("PREDEFINITO")) {
                lblInfoIndirizzo.setText(cliente.indirizzo.toString());
            } else if (controllaIndirizzo(via, citta, civico, cap)) {
                Indirizzo indirizzo = new Indirizzo(via, citta, civico, cap, prov);
                lblInfoIndirizzo.setText(indirizzo.toString());
            }
        }
    }

        public void actionInserisciData(ActionEvent actionEvent) {
            lblData.setText("");
            lblInfoData.setText("");
            if(dpData.getValue().isBefore(LocalDate.now())||dpData.getValue() == null){
                lblData.setText("Data non valida");
            }else
                lblInfoData.setText(dpData.getValue().toString());
        }


    private void inserisciEmail(String text) throws SQLException {
        lblEmail.setText(" ");
        List<Cliente> unico = new ArrayList<>();
        String email = text.toUpperCase();
        if (!email.isBlank()) {
            if (controllerCliente.cercaCliente(email)) {
                btnInserisci1.setDisable(false);
                btnInserisci2.setDisable(false);
                cliente = controllerCliente.prendiCliente(email);
                lblInfoNome.setText(cliente.denominazione);
                unico.add(cliente);
                clienteDisponibili = FXCollections.observableArrayList(unico);
                tabellaCliente.setItems(clienteDisponibili);
            } else
                lblEmail.setText(email + " non esistente nel sistema");
        }else
            lblEmail.setText("Questo campo non puo' essere vuoto");
    }

    public void actionCopri() {
        hboxNascosta.setDisable(!selezionaIndirizzo.getValue().contains("NUOVO"));
        lblInfoIndirizzo.setText("");

    }

    public void actionResetta() throws SQLException {
        settaClienti();
        lblInfoIndirizzo.setText("");
        lblInfoData.setText("");
        lblData.setText("");
        lblEmail.setText("");
        lblInfoNome.setText("");
        lblErroreIndirizzo.setText("");
        txtEmail.clear();
        txtCivico.clear();
        txtCap.clear();
        txtCitta.clear();
        txtId.clear();
        txtVia.clear();
        dpData.setValue(LocalDate.now());
    }

    public void actionAnnulla() throws SQLException {
        String id = txtId.getText();
        if (pacchiNonAssegnati.stream().anyMatch(p -> p.id == (Integer.parseInt(id)))) {
            Pacco pacco = pacchiNonAssegnati.stream().filter(p -> p.id == (Integer.parseInt(id))).findAny().get();
            pacchiNonAssegnati.remove(pacco);
            new GestorePacco().delete(Integer.parseInt(id));
        }
    }

    public void actionCreaPacco() throws SQLException {
        creaPacco();
    }

    /**
     * Creazione di un pacco. Controlla se i valori inseriti sono corretti.
     *
     * @throws SQLException
     */
    private void creaPacco() throws SQLException {
        lblErroreIndirizzo.setText("");
        String email = txtEmail.getText().toUpperCase();
        LocalDate data = dpData.getValue();
        String via = txtVia.getText();
        String citta = txtCitta.getText();
        String prov = chbxProvincia.getValue();
        String civico = txtCivico.getText();
        String cap = txtCap.getText();
        if(!(lblInfoNome.getText().isBlank()||lblInfoData.getText().isBlank()||lblInfoIndirizzo.getText().isBlank())){
            lblErrore.setText("");
            if (controllaInfo(email, data, via, citta, civico, cap)) {
                Cliente cliente = controllerCliente.prendiCliente(email);
                Pacco pacco;
                Date date = Date.from(data.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                if (selezionaIndirizzo.getValue().equals("NUOVO")) {
                    Indirizzo indirizzo = new Indirizzo(via, civico, citta, cap, prov);
                    pacco = new Pacco(cliente, getNegozio(), date, indirizzo);
                } else
                    pacco = new Pacco(cliente, getNegozio(), date, cliente.indirizzo);
                if (recapInfo(pacco) == ButtonType.OK) {
                    new GestorePacco().save(pacco);
                    settaPacchi(getNegozio());
                }
            }
        }else
            lblErrore.setText("INSERISCI INFO");

    }


    /**
     * Mostra le informazioni del pacco da creare
     *
     * @param pacco pacco da creare
     * @throws SQLException
     */
    private ButtonType recapInfo(Pacco pacco){
        Alert alert = new Alert(AlertType.NONE,
                "Spedito da: " + pacco.mittente.denominazione
                        + "\nDestinatario: " + pacco.destinatario.eMail
                        + "\nConsegna: " + pacco.dataConsegnaRichiesta + "\n"
                        + pacco.indirizzo.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
    }



    /**
     * Controlla le informazioni
     *
     * @param email  email inserita
     * @param data   data inserita -> può essere l'odierna o futura
     * @param via    via inserita
     * @param citta  citta inserita
     * @param civico civico inserito
     * @param cap    cap inserito -> 5 caratteri
     * @return true se le informazioni sono valide
     */
    private boolean controllaInfo(String email, LocalDate data, String via, String citta, String civico, String cap) throws SQLException {
        if (!email.isBlank())
            if (controllerCliente.cercaCliente(email))
                if (data.isAfter(LocalDate.now()))
                    if (selezionaIndirizzo.getValue().equals("NUOVO")) {
                        if (controllaIndirizzo(via, citta, civico, cap))
                            return true;
                    }else
                            return true;
        return false;
    }

    private boolean controllaIndirizzo(String via, String citta, String civico, String cap){
        if (!(via.isBlank() || citta.isBlank())) {
            if (!(civico.isBlank())) {
                if (cap.length() != 5 || !(cap.isBlank())) {
                    return true;
                } else
                    lblErroreIndirizzo.setText("Cap non valido");
            } else
                lblErroreIndirizzo.setText("Civico non valido");
        } else
            lblErroreIndirizzo.setText("Info non valide");
        return false;
    }

    /**
     * Inizializza i dati dell'account
     *
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        settaPacchi(getNegozio());
        settaClienti();
        chbxProvincia.setValue(account.indirizzo.provincia);
        btnInserisci1.setDisable(true);
        btnInserisci2.setDisable(true);
    }

    /**
     * @return negozio
     */
    public Negozio getNegozio() {
        return negozio;
    }

    /**
     * Setta l'account se e' un'istanza di Negozio
     *
     * @param account account passato
     */
    public void setNegozio(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        }
    }

}
