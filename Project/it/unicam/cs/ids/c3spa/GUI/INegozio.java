package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCategoriaNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.controller.NegozioController;
import it.unicam.cs.ids.c3spa.gestori.GestoreNegozio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class INegozio implements FXStage {

    private static INegozio istanza;
    private Negozio negozio;
    private NegozioController controller = new NegozioController();
    @FXML
    private TextField txtMail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblLogin, lblUtente, lblErrore2, lblErrore3;
    @FXML
    private Button btnAccedi, btnCreaOrdine, btnCatAttive, btnStorico, btnSconti, btnOrdini, btnListaCliente, btnModifica, btnPromozioni;

    public INegozio() {
    }

    public static INegozio getInstance() {
        if (istanza == null) {
            istanza = new INegozio();
        }
        return istanza;
    }

    public void actionAccedi() throws SQLException, IOException {
      accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    public void actionOrdine() throws IOException, SQLException {
        apriStageController("resources/creaOrdine.fxml", new IOrdine(), getNegozio());
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
        apriStageController("resources/pubblicita.fxml", new IPubblicita(), getNegozio());
    }

    public void actionModificaInfo() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            apriStageController("resources/aggiornaDati.fxml", new IModificaInfo(), getNegozio());
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            attuale.close();
        }
    }

    public void actionContatti() throws IOException, SQLException {
        //CONTROLLO SOLO PERCHè NON ABBIAMO TUTTI I GESTORI
        if(getNegozio().indirizzo.provincia.equals("MC") ||
                getNegozio().indirizzo.provincia.equals("AP") ||
                getNegozio().indirizzo.provincia.equals("PU") ||
                getNegozio().indirizzo.provincia.equals("AN") ||
                getNegozio().indirizzo.provincia.equals("FM")) {
            apriStageController("resources/contatti.fxml", new IContatti(), getNegozio());
        }
    }

    public void actionListaPromozioni() throws IOException, SQLException {
        apriStageController("resources/promozioni.fxml", new IPromozioni(), getNegozio());
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
                setNegozio(controller.prendiNegozio(email));
                apriStageController("resources/negozio.fxml", getInstance(), getNegozio());
            }
        }
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
