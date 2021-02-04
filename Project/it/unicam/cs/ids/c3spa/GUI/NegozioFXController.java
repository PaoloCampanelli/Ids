package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCategoriaNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NegozioFXController implements FXStage {

    private static NegozioFXController istanza;
    private Negozio negozio;
    @FXML
    private TextField txtMail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblLogin, lblUtente, lblErrore2, lblErrore3, lblToken;
    @FXML
    private Button btnAccedi, btnCreaOrdine, btnCatAttive, btnStorico, btnSconti, btnOrdini, btnListaCliente, btnModifica, btnPromozioni;

    public NegozioFXController() {
    }

    public static NegozioFXController getInstance() {
        if (istanza == null) {
            istanza = new NegozioFXController();
        }
        return istanza;
    }

    public void actionAccedi() throws SQLException, IOException {
      accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    public void actionOrdine() throws IOException, SQLException {
        apriStageController("resources/creaOrdine.fxml", new OrdineFXController(), getNegozio());
    }

    public void actionListaClienti() throws IOException, SQLException {
        apriStageController("resources/tabellaClienti.fxml", new TabNegoziFXController(), getNegozio());
    }

    public void actionListaCategorie() throws IOException, SQLException {
        apriStageController("resources/tabellaCategoriaNegozi.fxml", new TabCategoriaNegoziFXController(), getNegozio());
    }

    public void actionStoricoOrdiniNegozio() throws IOException, SQLException {
        apriStageController("resources/tabellaStorico.fxml", new TabStoricoFXController(), getNegozio());
    }

    public void actionAttivaPubblicita() {
        //TODO
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), getNegozio());
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            attuale.close();
        }
    }

    public void actionContatti() throws IOException {
        apriStage("resources/contatti.fxml", new ContattiFXController());
    }

    public void actionListaPromozioni() throws IOException, SQLException {
        apriStageController("resources/sconti.fxml", new ScontiFXControllerFXStage(), getNegozio());
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
    private void accedi(String email, String password) throws SQLException, IOException {
        if ((controllaInfo(email, password))) {
            if (controllaNegozio(email, password)) {
                setNegozio(prendiNegozio(email));
                apriStageController("resources/negozio.fxml", getInstance(), getNegozio());
            }
        }
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
     * Controlla se il negozio esiste tramite email e password
     *
     * @param email    email inserita
     * @param password password inserita
     * @return true
     * se il negozio esiste
     * @throws SQLException
     */
    private boolean controllaNegozio(String email, String password) throws SQLException {
        List<Negozio> ln = new GestoreNegozio().getAll();
        return ln.stream().anyMatch(c -> {
            try {
                return c.eMail.equals(email) && password.equals(new Servizi().decrypt(password));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    /**
     * Prende il negozio tramite l'email
     *
     * @param email email passata
     * @return Negozio
     * @throws SQLException
     */
    private Negozio prendiNegozio(String email) throws SQLException {
        GestoreNegozio gc = new GestoreNegozio();
        List<Negozio> lc = gc.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email)).findAny().get().id;
        return gc.getById(id);
    }

    /**
     * @return negozio
     */
    public Negozio getNegozio() {
        return negozio;
    }

    /**
     * Se account e' un'istanza di Negozio la istanza.
     *
     * @param account
     */
    public void setNegozio(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        }
    }

    /**
     * Inizializza i dati dell'account
     *
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        lblUtente.setText(account.denominazione);
        setNegozio(account);
        lblToken.setText("Token disponibili: " + getNegozio().token);
    }


}
