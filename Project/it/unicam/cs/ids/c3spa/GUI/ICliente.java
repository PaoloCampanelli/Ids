package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.*;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.controller.ClienteController;
import it.unicam.cs.ids.c3spa.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ICliente implements FXStage {

    private static ICliente istanza;

    private ObservableList<Negozio> listaNegozi;
    private ClienteController controller = new ClienteController();

    private Cliente cliente;
    @FXML
    private TextField txtMail, txtCategoriaCitta, txtCitta, txtCategoria;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnAccedi, btnRicerca1, btnRicerca2, btnRicerca3, btnRicerca4, btnModifica, btnStorico, btnOrdini, btnSconti;
    @FXML
    private Label lblLogin, lblUtente, lblCittaUtente, lblErrore1, lblErrore2;
    @FXML
    private TableView<Negozio> tabellaPubblicita;
    @FXML
    private TableColumn<Negozio, String> tbNome;
    @FXML
    private TableColumn<Negozio, String> tbIndirizzo;
    @FXML
    private TableColumn<Negozio, String> tbCategorie;

    public ICliente() {
    }

    /**
     * Singleton
     *
     * @return l'istanza di ClienteFXController
     */
    public static ICliente getInstance() {
        if (istanza == null) {
            istanza = new ICliente();
        }
        return istanza;
    }

    public void actionAccedi() throws IOException, SQLException {
        accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    public void actionRicercaNegozi() throws IOException, SQLException {
        apriStageController("resources/tabellaNegozi.fxml", new TabNegoziFXController(), getCliente());
    }
    
    public void actionRicercaNegoziByCitta() throws SQLException, IOException {
        apriStageController("resources/tabellaCitta.fxml", new TabCittaFXController(), getCliente());
    }

    public void actionRicercaCategoria() throws IOException, SQLException {
        lblErrore2.setText("");
        if (!(txtCategoria.getText().isBlank()))
            apriStage("resources/tabellaCategoria.fxml", "", txtCategoria.getText().toUpperCase(), "CATEGORIA");
        else
            lblErrore2.setText("Valore non valido!");
    }

    public void actionRicercaCittaCategoria() throws IOException, SQLException {
        lblErrore1.setText("");
        if (!(txtCitta.getText().isBlank() || txtCategoriaCitta.getText().isBlank()))
            apriStage("resources/tabellaCittaCategoria.fxml", txtCitta.getText().toUpperCase(), txtCategoriaCitta.getText().toUpperCase(), "CITTA");
        else
            lblErrore1.setText("Valori non validi!");
    }

    public void actionStoricoOrdiniCliente() throws IOException, SQLException {
        apriStageController("resources/tabellaStorico.fxml", new TabStoricoFXController(), getCliente());
    }

    public void actionOrdiniAttivi() throws IOException, SQLException {
        apriStageController("resources/tabellaOrdine.fxml", new TabOrdineFXController(), getCliente());
    }

    public void actionVisualizzaSconti() throws IOException, SQLException {
        apriStage("resources/switchSconti.fxml", this);
    }

    public void actionProvincia(ActionEvent actionEvent) throws IOException, SQLException {
        apriStageController("resources/tabellaScontiProvincia.fxml", new TabScontiProvinciaFXController(), getCliente());
    }

    public void actionTutti(ActionEvent actionEvent) throws IOException, SQLException {
        apriStageController("resources/tabellaSconti.fxml", new TabScontiFXController(), getCliente());
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            apriStageController("resources/aggiornaDati.fxml", new IModificaInfo(), getCliente());
            attuale.close();
        }
    }

    public void actionContatti() throws IOException, SQLException {
        //CONTROLLO SOLO PERCHè NON ABBIAMO TUTTI I GESTORI
        if(getCliente().indirizzo.provincia.equals("MC") ||
                getCliente().indirizzo.provincia.equals("AP") ||
                getCliente().indirizzo.provincia.equals("PU") ||
                getCliente().indirizzo.provincia.equals("AN") ||
                getCliente().indirizzo.provincia.equals("FM")) {
            apriStageController("resources/contatti.fxml", new IContatti(), getCliente());
        }
    }


    /**
     * Inizializza i dati dell'account
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        settaNegozi();
        lblCittaUtente.setText(account.indirizzo.citta);
        lblUtente.setText(account.denominazione);

    }

    private void apriStage(String fxml, String citta, String categoria, String tipo) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
        stage.setScene(new Scene(loader.load()));
        if(tipo.equals("CATEGORIA")) {
            TabCategoriaFXController controller = loader.getController();
            controller.initData(categoria);
        }else if(tipo.equals("CITTA")){
            TabCittaCategoriaFXController controller = loader.getController();
            controller.initData(citta, categoria);
        }
        stage.setTitle("C3");
        stage.show();
    }

    /**
     * Setta un ObservableList di Pubblicita'
     * @throws SQLException
     */
    private void settaNegozi() throws SQLException {
        List<Negozio> negozi = new GestorePubblicita().getNegoziConPubblicitaAttivaByString("`indirizzo.provincia`", getCliente().indirizzo.provincia);
        listaNegozi = FXCollections.observableArrayList(negozi);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
        tbCategorie.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categorie.toString()));
        tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaPubblicita.setItems(listaNegozi);
        tabellaPubblicita.setPlaceholder(new Label("Non ci sono pubblicita'!"));
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
        List<Cliente> lc = new GestoreCliente().getAll();
        if ((controllaInfo(email, password))) {
            if (cercaAccount(lc, email, password)) {
                setCliente(controller.prendiCliente(email));
                apriStageController("resources/cliente.fxml", getInstance(), getCliente());
            } else
                lblLogin.setText("Email e/o password non corretto");
        } else
            lblLogin.setText("Errore nell'inserimento dati");
    }

    /**
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }


    /**
     * Setta l'account se e' un cliente
     *
     * @param account account passato
     */
    public void setCliente(Account account) {
        if (account instanceof Cliente) {
            this.cliente = (Cliente) account;
        }
    }

}

