package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCategoriaNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
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
    private Label lblLogin, lblUtente, lblErrore2, lblErrore3;
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

    public void actionAttivaPubblicita() throws IOException, SQLException {
        apriStageController("resources/pubblicita.fxml", new PubblicitaFXController(), getNegozio());
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), getNegozio());
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            attuale.close();
        }
    }

    public void actionContatti() throws IOException, SQLException {
        apriStageController("resources/contatti.fxml", new ContattiFXController(), new GestoreAmministratore().getById(1));
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
        List<Negozio> ln = new GestoreNegozio().getAll();
        if ((controllaInfo(email, password))) {
            if (cercaAccount(ln, email, password)) {
                setNegozio(prendiNegozio(email));
                apriStageController("resources/negozio.fxml", getInstance(), getNegozio());
            }
        }
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
        int id = lc.stream().filter(c -> {
            try {
                return c.eMail.equals(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }).findAny().get().id;
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
    }


}
