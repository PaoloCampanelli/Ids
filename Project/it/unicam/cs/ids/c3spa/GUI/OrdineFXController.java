package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class OrdineFXController implements FXStage {

    private Negozio negozio;
    private ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("PREDEFINITO", "NUOVO");
    private ObservableList<String> provincia = FXCollections.observableArrayList(
            "AG", "AL","AN","AO", "AQ", "AR", "AP", "AT","AV","BA","BT", "BL", "BN", "BG","BI", "BO", "BZ", "BS", "BR", "CA", "CL", "CB", "CI", "CE", "CT", "CZ", "CH", "CO", "CS", "CR", "KR", "CN", "EN", "FM", "FE", "FI",
            "FG", "FC", "FR", "GE", "GO", "GR", "IM", "IS", "SP", "LT", "LE", "LC", "LI", "LO", "LU", "MC", "MN", "MS", "MT",  "ME", "MI", "MO", "MB", "NA", "NO", "NU", "OG", "OT", "OR", "PD", "PA", "PR", "PV", "PG", "PU", "PE",
            "PC", "PI", "PT", "PN", "PZ", "PO", "RG", "RA", "RC", "RE", "RI", "RN", "RM", "RO", "SA", "SS", "SV", "SI", "SR", "SO", "TA", "TE", "TR", "TO", "TP", "TN", "TV", "TS", "UD", "VA", "VE", "VB", "VC","VS", "VR", "VV", "VI", "VT");
    private ObservableList<Pacco> pacchiNonAssegnati;
    private ObservableList<Cliente> clienteDisponibili;
    @FXML
    private TableView<Cliente> tabellaCliente;
    @FXML
    private TableView<Pacco> tabellaPacchi;
    @FXML
    private TableColumn<Cliente, String> tbNome;
    @FXML
    private TableColumn<Cliente, String> tbIndirizzo;
    @FXML
    private TableColumn<Cliente, String> tbEmail;
    @FXML
    private TextField txtEmail, txtId, txtEmailRicerca, txtVia, txtCitta, txtCivico, txtProv, txtCap;
    @FXML
    private DatePicker dpData;
    @FXML
    private Button btnRicerca, btnAnnulla, btnCrea, btnResetta, btnConferma;
    @FXML
    private HBox hboxNascosta;
    @FXML
    private Label lblAnnulla, lblEmail, lblErrore, lblErroreIndirizzo, lblData, infoEmail, infoData, infoIndirizzo;
    @FXML
    private TableColumn<Pacco, String> tbId;
    @FXML
    private TableColumn<Pacco, String> tbDestinatario;
    @FXML
    private TableColumn<Pacco, String> tbIndirizzoPacco;
    @FXML
    private TableColumn<Pacco, String> tbDataPacco;
    @FXML
    private ChoiceBox<String> selezionaIndirizzo;
    @FXML
    private ChoiceBox<String> chbxProvincia;

    public OrdineFXController() {
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
        List<Pacco> pacco = new GestorePacco().getByMittente(negozio);
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

    public void actionRicerca() throws SQLException {
        settaClienti();
        lblEmail.setText(" ");
        List<Cliente> unico = new ArrayList<>();
        String email = txtEmail.getText().toUpperCase();
        if (!email.isBlank()) {
            if (clienteEsistente(email)) {
                infoEmail.setText(email);
                Cliente cliente = prendiCliente(email);
                infoIndirizzo.setText(cliente.indirizzo.toString());
                unico.add(cliente);
                clienteDisponibili = FXCollections.observableArrayList(unico);
                tabellaCliente.setItems(clienteDisponibili);
            } else {
                lblEmail.setText(email + " non esistente nel sistema");
            }
        }else
            settaClienti();

    }

    public void actionCopri() {
        hboxNascosta.setDisable(!selezionaIndirizzo.getValue().contains("NUOVO"));
    }

    public void actionResetta() throws SQLException {
        settaClienti();
    }

    public void actionAnnulla() throws SQLException {
        int id = Integer.parseInt(txtId.getText());
        annullaPacco(id);
    }

    private void annullaPacco(int id) throws SQLException {
        settaPacchi(getNegozio());
        if (pacchiNonAssegnati.stream().anyMatch(p -> p.id == id)) {
            Pacco pacco = pacchiNonAssegnati.stream().filter(p -> p.id == id).findAny().get();
            pacchiNonAssegnati.remove(pacco);
            new GestorePacco().delete(id);
        }
    }

    public void actionCreaPacco() throws SQLException {
        creaPacco();
    }

   public void actionIndirizzo(){
        //impostaPacco();
   }



    public void actionSettaData() {
        lblData.setText("");
        if(dpData.getValue().isBefore(LocalDate.now())){
            lblData.setText("Data non valida");
        }else{
            infoData.setText(dpData.getValue().toString());
        }
    }

    /**
     * Creazione di un pacco. Controlla se i valori inseriti sono corretti.
     *
     * @throws SQLException
     */
    private void creaPacco() throws SQLException {
        String email = txtEmail.getText().toUpperCase();
        LocalDate data = dpData.getValue();
        String via = txtVia.getText();
        String citta = txtCitta.getText();
        String prov = txtProv.getText();
        String civico = txtCivico.getText();
        String cap = txtCap.getText();
        if (controllaInfo(email, data, via, citta, prov, civico, cap)) {
            lblErrore.setText("");
            lblErroreIndirizzo.setText("");
            Cliente cliente = prendiCliente(email);
            Pacco pacco;
            Date date = Date.from(data.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            if (selezionaIndirizzo.getValue().equals("NUOVO")) {
                Indirizzo indirizzo = new Indirizzo(via, civico, citta, cap, prov);
                pacco = new Pacco(cliente, getNegozio(), date, indirizzo);
            } else
                pacco = new Pacco(cliente, getNegozio(), date, cliente.indirizzo);
            if(recapInfo(pacco) == ButtonType.OK){
                    new GestorePacco().save(pacco);
                    settaPacchi(getNegozio());
            }
        }
    }


    /**
     * Mostra le informazioni del pacco da creare
     *
     * @param pacco pacco da creare
     * @throws SQLException
     */
    private ButtonType recapInfo(Pacco pacco){
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Spedito da: " + pacco.mittente.denominazione
                        + "\nDestinatario: " + pacco.destinatario.eMail
                        + "\nConsegna: " + pacco.dataConsegnaRichiesta + "\n"
                        + pacco.indirizzo.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
    }

    /**
     * @param email email inserita
     * @return account con email associata
     */
    private Cliente prendiCliente(String email) {
        return clienteDisponibili.stream().filter(c -> c.eMail.toUpperCase().equals(email)).findAny().get();
    }

    /**
     * controlla se il cliente con l'email inserita esiste
     *
     * @param email email inserita
     * @return true se il cliente esiste
     */
    private boolean clienteEsistente(String email) {
        return clienteDisponibili.stream().anyMatch(c -> c.eMail.toUpperCase().equals(email));
    }

    /**
     * Controlla le informazioni
     *
     * @param email  email inserita
     * @param data   data inserita -> può essere l'odierna o futura
     * @param via    via inserita
     * @param citta  citta inserita
     * @param prov   provincia inserita -> 2 caratteri
     * @param civico civico inserito
     * @param cap    cap inserito -> 5 caratteri
     * @return true se le informazioni sono valide
     */
    private boolean controllaInfo(String email, LocalDate data, String via, String citta, String prov, String civico, String cap) {
        if (!email.isBlank()) {
            if (clienteEsistente(email)) {
                if (data.isAfter(LocalDate.now())) {
                    if (selezionaIndirizzo.getValue().equals("NUOVO")) {
                        if (!(via.isBlank() || citta.isBlank())) {
                            if ((prov.length() == 2) || !(prov.isBlank())) {
                                if (!(civico.isBlank())) {
                                    if (cap.length() != 5 || !(cap.isBlank())) {
                                        //impostaPacco();

                                        return true;
                                    } else
                                        lblErroreIndirizzo.setText("Cap non valido");
                                } else
                                    lblErroreIndirizzo.setText("Civico non valido");
                            } else
                                lblErroreIndirizzo.setText("Provincia non valida");
                        } else
                            lblErroreIndirizzo.setText("Info non valide");
                    } else
                        return true;
                }
            } else
                lblEmail.setText("Cliente non esistente");
        } else
            lblEmail.setText("Email non valida");
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
