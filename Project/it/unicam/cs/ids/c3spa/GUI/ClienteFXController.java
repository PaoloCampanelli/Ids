package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.*;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClienteFXController implements FXStage {

    private static ClienteFXController istanza;
    private Cliente cliente;
    @FXML
    private TextField txtMail, txtCategoriaCitta, txtCitta, txtCategoria;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnAccedi, btnRicerca1, btnRicerca2, btnRicerca3, btnRicerca4, btnModifica, btnStorico, btnOrdini, btnSconti;
    @FXML
    private Label lblLogin, lblUtente, lblCittaUtente, lblErrore1, lblErrore2;
    public ClienteFXController() {
    }

    /**
     * Singleton
     *
     * @return l'istanza di ClienteFXController
     */
    public static ClienteFXController getInstance() {
        if (istanza == null) {
            istanza = new ClienteFXController();
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
            apriStage("resources/tabellaCategoria.fxml", " ", txtCategoria.getText().toUpperCase(), new TabCategoriaFXController());
        else
            lblErrore2.setText("Valore non valido!");
    }


    public void actionRicercaCittaCategoria() throws IOException, SQLException {
        lblErrore1.setText("");
        if (!(txtCitta.getText().isBlank() || txtCategoriaCitta.getText().isBlank()))
            apriStage("resources/tabellaCittaCategoria.fxml", txtCitta.getText().toUpperCase(), txtCategoriaCitta.getText().toUpperCase(), new TabCittaCategoriaFXController());
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
        apriStageController("resources/tabellaSconti.fxml", new TabScontiFXController(), getCliente());
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), getCliente());
            attuale.close();
        }
    }

    public void actionContatti() throws IOException {
        apriStage("resources/contatti.fxml", new ContattiFXController());
    }



    /**
     * Inizializza i dati dell'account
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        lblCittaUtente.setText(account.indirizzo.citta);
        lblUtente.setText(account.denominazione);

    }

    private void apriStage(String fxml, String citta, String categoria, FXTabella controller) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        controller.initData(getCliente(), citta, categoria);
        stage.setTitle("C3");
        stage.show();
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
        if ((controllaInfo(email, password))) {
            if (controllaCliente(email, password)) {
                setCliente(prendiCliente(email, password));
                apriStageController("resources/cliente.fxml", getInstance(), getCliente());
            } else
                lblLogin.setText("Email e/o password non corretto");
        } else
            lblLogin.setText("Errore nell'inserimento dati");
    }

    /**
     * Controlla se esiste un cliente con email e password inserite
     *
     * @param email    email inserita
     * @param password password inserita
     * @return true
     * se il cliente esiste
     * @throws SQLException
     */
    private boolean controllaCliente(String email, String password) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        return lc.stream().anyMatch(c -> c.eMail.equals(email) && c.password.equals(password));
    }

    /**
     * Prende il cliente corrispondente
     *
     * @param email    email inserita
     * @param password password inserita
     * @return cliente con email e password corrispondenti
     * @throws SQLException
     */
    private Cliente prendiCliente(String email, String password) throws SQLException {
        GestoreCliente gc = new GestoreCliente();
        List<Cliente> lc = gc.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email) && c.password.equals(password)).findAny().get().id;
        return gc.getById(id);
    }

    /**
     * Controlla i parametri passati
     *
     * @param email    email inserita
     * @param password password inserita
     * @return true
     * se l'email e la password non e' vuota, password maggiore di 6 caratteri
     */
    private boolean controllaInfo(String email, String password) {
        return !email.isBlank() && !password.isBlank() && password.length() >= 6;
    }

    /**
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Setta il cliente, se la mail corrisponde ad un account esistente
     *
     * @param email email inserita
     * @throws SQLException eccezione
     */
    public void setCliente(String email) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getByEMail(email);
        for (Cliente cliente : lc) {
            this.cliente = cliente;
        }
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

