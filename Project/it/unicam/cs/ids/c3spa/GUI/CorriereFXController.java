package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CorriereFXController implements FXStage {

    private static CorriereFXController istanza;
    private Corriere corriere;
    private ObservableList<Pacco> pacchiNonAssegnati;
    private ObservableList<Pacco> ordiniCorriere;
    @FXML
    private TextField txtMail, txtIDConsegna, txtIDAnnulla, txtPaccoPreso;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblLogin, lblUtente;
    @FXML
    private Button btnModifica, btnStorico, btnConsegna1, btnConsegna2, btnConsegna3, btnAccedi;
    @FXML
    private VBox vbox3;
    @FXML
    private TableView<Pacco> tabellaPacchi;
    @FXML
    private TableColumn<Pacco, String> tbPID;
    @FXML
    private TableColumn<Pacco, String> tbPNegozio;
    @FXML
    private TableColumn<Pacco, String> tbPDestinatario;
    @FXML
    private TableColumn<Pacco, String> tbPConsegna;
    @FXML
    private TableColumn<Pacco, String> tbPCitta;
    @FXML
    private TableView<Pacco> tabellaOrdini;
    @FXML
    private TableColumn<Pacco, String> tbOID;
    @FXML
    private TableColumn<Pacco, String> tbOIndirizzo;
    @FXML
    private TableColumn<Pacco, String> tbODestinatario;
    @FXML
    private TableColumn<Pacco, String> tbOConsegna;

    public CorriereFXController() {
    }

    /**
     * Singleton
     *
     * @return l'istanza di CorriereFXController
     */
    public static CorriereFXController getInstance() {
        if (istanza == null) {
            istanza = new CorriereFXController();
        }
        return istanza;
    }

    /**
     * Setta una ObservableList<Pacco>
     *
     * @throws SQLException
     */
    public void settaPacchi() throws SQLException {
        List<Pacco> pacco = new GestorePacco().getPacchiSenzaCorriere();
        pacchiNonAssegnati = FXCollections.observableArrayList(pacco);
        tbPID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbPDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
        tbPConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
        tbPNegozio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
        tbPCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        pacchiNonAssegnati.removeIf(p -> p.id == 0);
        tabellaPacchi.setItems(pacchiNonAssegnati);
        tabellaPacchi.setPlaceholder(new Label("C3 non contiene pacchi!"));
    }

    /**
     * Setta una ObservableList
     *
     * @param corriere corriere corrispondente
     * @throws SQLException
     */
    private void settaOrdini(Corriere corriere) throws SQLException {
        List<Pacco> pacco = new GestorePacco().getByCorriere(corriere);
        ordiniCorriere = FXCollections.observableArrayList(pacco);
        tbOID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbODestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
        tbOConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
        tbOIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        ordiniCorriere.removeIf(p -> p.id == 0);
        tabellaOrdini.setItems(ordiniCorriere);
        tabellaOrdini.setPlaceholder(new Label("Non hai preso in carico nessun pacco!"));
    }

    public void actionAccedi() throws SQLException, IOException {
        accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    public void actionAssegna() throws SQLException {
        if (cercaPacco(txtPaccoPreso.getText())) {
            Pacco pacco = prendiPacco(txtPaccoPreso.getText());
            getCorriere().prendiPacco(pacco);
            ordiniCorriere.add(pacco);
            new GestorePacco().save(pacco);
            pacchiNonAssegnati.removeIf(p -> p.id == pacco.id);
        }
    }

    public void actionConsegna() throws SQLException {
        if (cercaPacco(txtIDConsegna.getText())) {
            Pacco pacco = prendiPacco(txtIDConsegna.getText());
            avvertiConsegna(pacco);
        }
    }

    public void actionAnnulla() throws SQLException {
        if (cercaPacco(txtIDAnnulla.getText())) {
            Pacco pacco = prendiPacco(txtIDAnnulla.getText());
            pacco.corriere = null;
            pacchiNonAssegnati.add(pacco);
            new GestorePacco().save(pacco);
            new GestoreCorriere().save(pacco);
            ordiniCorriere.removeIf(p -> p.id == pacco.id);

        }
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), getCorriere());
            attuale.close();
        }

    }

    public void actionStoricoOrdiniNegozio() throws IOException, SQLException {
        apriStageController("resources/tabellaStorico.fxml", new TabStoricoFXController(), getCorriere());
    }

    public void actionContatti() throws IOException, SQLException {
        //CONTROLLO SOLO PERCHè NON ABBIAMO TUTTI I GESTORI
        if(getCorriere().indirizzo.provincia.equals("MC") ||
                getCorriere().indirizzo.provincia.equals("AP") ||
                getCorriere().indirizzo.provincia.equals("PU") ||
                getCorriere().indirizzo.provincia.equals("AN") ||
                getCorriere().indirizzo.provincia.equals("FM")){
            apriStageController("resources/contatti.fxml", new ContattiFXController(), getCorriere());
        }

    }

    /**
     * Apre un alert per confermare i dati di consegna
     *
     * @param pacco pacco da consegnare
     * @throws SQLException
     */
    private void avvertiConsegna(Pacco pacco) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Spedito da: " + pacco.mittente.denominazione + "									"
                        + "Destinatario: " + pacco.destinatario.eMail
                        + " Consegna: " + pacco.dataConsegnaRichiesta + " "
                        + pacco.indirizzo.toString(), ButtonType.YES, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            getCorriere().consegnaPacco(pacco);
            new GestorePacco().save(pacco);
            ordiniCorriere.removeIf(p -> p.id == pacco.id);
        }
    }

    /**
     * Permette di accedere dopo il controllo delle informazioni inserite
     * @param email
     *          email inserita
     * @param password
     *          password inserita
     * @throws SQLException
     * @throws IOException
     */
    private void accedi(String email, String password)  throws SQLException, IOException {
        List<Corriere> lc = new GestoreCorriere().getAll();
        if ((controllaInfo(email, password))) {
            if (cercaAccount(lc, email, password)) {
                setCorriere(prendiCorriere(email));
                apriStageController("resources/corriere.fxml", getInstance(), getCorriere());
            }
        }
    }


    /**
     * Controlla se un pacco con id corrispondente esiste
     *
     * @param id id del pacco da ricercare
     * @return true
     * se il pacco esiste
     * @throws SQLException
     */
    private boolean cercaPacco(String id) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getAll();
        return pacchi.stream().anyMatch(p -> p.id == Integer.parseInt(id));
    }

    /**
     * Prende il pacco con id corrispondente
     *
     * @param id id del pacco da prendere
     * @return pacco assegnato
     * @throws SQLException
     */
    private Pacco prendiPacco(String id) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getAll();
        int idPacco = pacchi.stream().filter(p -> p.id == Integer.parseInt(id)).findAny().get().id;
        return new GestorePacco().getById(idPacco);
    }




    /**
     * Prende il corriere con email corrispondente
     *
     * @param email email inserita
     * @return Corriere corrispondente
     * @throws SQLException
     */
    private Corriere prendiCorriere(String email) throws SQLException {
        GestoreCorriere gc = new GestoreCorriere();
        List<Corriere> lc = gc.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email)).findAny().get().id;
        return gc.getById(id);
    }

    /**
     * @return corriere
     */
    public Corriere getCorriere() {
        return corriere;
    }

    /**
     * Setta l'account se e' un corriere
     *
     * @param account account passato
     */
    public void setCorriere(Account account) {
        if (account instanceof Corriere) {
            this.corriere = (Corriere) account;
        }
    }

    /**
     * Inizializza i dati dell'account, le liste e le etichette
     *
     * @param account account passato
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        lblUtente.setText(account.denominazione);
        setCorriere(account);
        settaPacchi();
        settaOrdini(getCorriere());
    }

}
