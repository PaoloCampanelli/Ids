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

public class OrdineFXController implements FXController {

    private Negozio negozio;
    private ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("PREDEFINITO", "NUOVO");
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
    private Label lblAnnulla, lblEmail, lblErrore, lblErroreIndirizzo;
    @FXML
    private TableColumn<Pacco, String> tbId;
    @FXML
    private TableColumn<Pacco, String> tbDestinatario;
    @FXML
    private TableColumn<Pacco, String> tbIndirizzoPacco;
    @FXML
    private ChoiceBox<String> selezionaIndirizzo;

    public OrdineFXController() {
    }

    @FXML
    public void initialize() {
        selezionaIndirizzo.setItems(tipologiaDisponibile);
        selezionaIndirizzo.setValue("PREDEFINITO");
        hboxNascosta.setDisable(true);
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

    public void actionRicerca() {
        lblEmail.setText(" ");
        List<Cliente> unico = new ArrayList<>();
        String email = txtEmail.getText().toUpperCase();
        if (!email.isBlank()) {
            if (clienteEsistente(email)) {
                Cliente cliente = prendiCliente(email);
                unico.add(cliente);
                clienteDisponibili = FXCollections.observableArrayList(unico);
                tabellaCliente.setItems(clienteDisponibili);
            } else {
                lblEmail.setText(email + " non esistente nel sistema");
            }
        }
    }

    public void actionCopri() {
        hboxNascosta.setDisable(!selezionaIndirizzo.getValue().contains("NUOVO"));

    }

    public void actionResetta() throws SQLException {
        settaClienti();
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
            recapInfo(pacco);
        }
    }

    /**
     * Mostra le informazioni del pacco da creare
     *
     * @param pacco pacco da creare
     * @throws SQLException
     */
    private void recapInfo(Pacco pacco) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Spedito da: " + pacco.mittente.denominazione + "									"
                        + "Destinatario: " + pacco.destinatario.eMail
                        + " Consegna: " + pacco.dataConsegnaRichiesta + " "
                        + pacco.indirizzo.toString(), ButtonType.YES, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            pacchiNonAssegnati.add(pacco);
            new GestorePacco().save(pacco);
        }
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
                } else
                    lblErrore.setText("Data non valida");
            } else
                lblErrore.setText("Email non valida");
        } else
            lblErrore.setText("Email non esistente nel sistema");
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
