package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
    TextField txtMail,txtPaccoPreso, txtIDConsegna;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label lblLogin, lblUtente, lblIDAssegna,  lblIDConsegna;
    @FXML
    Button btnModifica, btnStorico, btnConsegna1, btnConsegna2, btnConsegna3, btnAccedi;
    @FXML
    TableView<Pacco> tabellaPacchi;
    @FXML
    TableColumn<Pacco, String> tbPID;
    @FXML
    TableColumn<Pacco, String> tbPNegozio;
    @FXML
    TableColumn<Pacco, String> tbPDestinatario;
    @FXML
    TableColumn<Pacco, String> tbPConsegna;
    @FXML
    TableColumn<Pacco, String> tbPCitta;
    @FXML
    TableView<Pacco> tabellaOrdini;
    @FXML
    TableColumn<Pacco, String> tbOID;
    @FXML
    TableColumn<Pacco, String> tbOIndirizzo;
    @FXML
    TableColumn<Pacco, String> tbODestinatario;
    @FXML
    TableColumn<Pacco, String> tbOConsegna;

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
        try {
            int id = Integer.parseInt(txtPaccoPreso.getText());
            assegnaPacco(id);
        }catch (NumberFormatException e){
            lblIDAssegna.setText(txtPaccoPreso.getText()+" non valido");
        }
    }


    public void actionConsegna() throws SQLException {
        try{
            int id = Integer.parseInt(txtIDConsegna.getText());
            consegnaPacco(id);
        }catch(NumberFormatException e){
            lblIDConsegna.setText(txtIDConsegna+" non valido");
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
        apriStageController("resources/contatti.fxml", new ContattiFXController(), new GestoreAmministratore().getById(1));
    }



    private void assegnaPacco(int idCercato) throws SQLException {
        if (cercaPacco(idCercato)) {
            Pacco pacco = prendiPacco(idCercato);
            if(avvertiAssegnamento(pacco) == ButtonType.OK){
                getCorriere().prendiPacco(pacco);
                ordiniCorriere.add(pacco);
                new GestorePacco().save(pacco);
                pacchiNonAssegnati.removeIf(p -> p.id == pacco.id);
                }
            }else
                lblIDAssegna.setText("ID non trovato");
    }


    private void consegnaPacco(int id) throws SQLException {
        if (cercaPacco(id)) {
            Pacco pacco = prendiPacco(id);
            if (avvertiConsegna(pacco) == ButtonType.OK) {
                getCorriere().consegnaPacco(pacco);
                new GestorePacco().save(pacco);
                ordiniCorriere.removeIf(p -> p.id == pacco.id);
            }
        }else
            lblIDConsegna.setText("ID non trovato");
    }

    /**
     * Apre un alert per confermare i dati di consegna
     *
     * @param pacco pacco da consegnare
     * @throws SQLException
     */
    private ButtonType avvertiConsegna(Pacco pacco) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Spedito da: " + pacco.mittente.denominazione
                        + "\nDestinatario: " + pacco.destinatario.eMail
                        + "\nConsegna: " + pacco.dataConsegnaRichiesta
                        + "\n"+pacco.indirizzo.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
    }


    private ButtonType avvertiAssegnamento(Pacco pacco) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "ID: "+pacco.id+"\n"
                        +pacco.indirizzo.toString()
                        +"\nConsegna: "+pacco.dataConsegnaRichiesta.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
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
    private boolean cercaPacco(int id) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getAll();
        return pacchi.stream().anyMatch(p -> p.id == id);
    }

    /**
     * Prende il pacco con id corrispondente
     *
     * @param id id del pacco da prendere
     * @return pacco assegnato
     * @throws SQLException
     */
    private Pacco prendiPacco(int id) throws SQLException {
        List<Pacco> pacchi = new GestorePacco().getAll();
        int idPacco = pacchi.stream().filter(p -> p.id == id).findAny().get().id;
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
