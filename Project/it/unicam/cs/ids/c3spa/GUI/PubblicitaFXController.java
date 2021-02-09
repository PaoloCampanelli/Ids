package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pubblicita;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PubblicitaFXController implements FXStage{

    private ObservableList<Pubblicita> listaPubblicita;
    private Negozio negozio;

    @FXML
    private TextField txtID;
    @FXML
    private DatePicker dpInizio, dpFine;
    @FXML
    private Label lblToken, lblConta, lblPubblicita, lblID;
    @FXML
    private Button btnAttiva;
    @FXML
    private TableView<Pubblicita> tabellaPubblicita;
    @FXML
    private TableColumn<Pubblicita, String> tbID;
    @FXML
    private TableColumn<Pubblicita, String> tbInizio;
    @FXML
    private TableColumn<Pubblicita, String> tbFine;
    @FXML
    private ImageView logo;

    public void actionSettaToken() {
        Date dataInizio = Date.from(dpInizio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date dataFine = Date.from(dpFine.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        int tok = Pubblicita.tokenNecessari(dataInizio, dataFine);
        if(tok<0){
            lblConta.setText("DATE NON VALIDE");
        }else{
            lblConta.setText("NECESSARI: "+tok);
        }
    }

    public void actionAttiva() throws SQLException {
        settaPubblicita();
    }

    public void actionContatti() throws SQLException, IOException {
        apriStageController("resources/contatti.fxml", new ContattiFXController(), new GestoreAmministratore().getById(1));
    }

    public void actionAnnulla() {
        try {
            annullaPubblicita(Integer.parseInt(txtID.getText()));
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void settaTabella () throws SQLException {
        cercaPubblicita();
        List<Pubblicita> pubblicita = new GestorePubblicita().getPubblicitaAttive();
        listaPubblicita = FXCollections.observableArrayList(pubblicita);
        tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbInizio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataInizio.toString()));
        tbFine.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataFine.toString()));
        tabellaPubblicita.setItems(listaPubblicita);
        tabellaPubblicita.setPlaceholder(new Label("Non ci sono pubblicita'!"));
        lblToken.setText("I TUOI TOKEN: " + getNegozio().token);
    }

    private void settaPubblicita () throws SQLException {
        Date dataInizio = Date.from(dpInizio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date dataFine = Date.from(dpFine.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        if (dataInizio.before(dataFine)) {
            Pubblicita pubblicita = new Pubblicita(Servizi.dataUtilToSql(dataInizio), Servizi.dataUtilToSql(dataFine), getNegozio());
            if (getNegozio().token == 0) {
                lblConta.setText("CONTATTA L'ADMIN!");
            } else {
                if (recapInfo(pubblicita) == ButtonType.OK) {
                    new GestoreNegozio().creaPubblicita(pubblicita, getNegozio());
                }
            }
        }
        settaTabella();
    }

    private void annullaPubblicita(int idRicercato) throws SQLException {
        GestorePubblicita gp = new GestorePubblicita();
        List<Pubblicita> lp = gp.getPubblicitaByNegozio(getNegozio());
        if(idRicercato>0){
            if(lp.stream().anyMatch(p -> p.id == idRicercato)){
                Pubblicita pubblicita = lp.stream().filter(p -> p.id==idRicercato).findAny().get();
                if(alertElimina(pubblicita) == ButtonType.OK){
                    gp.delete(idRicercato);
                    settaTabella();
                }
            }
        }
    }

    private ButtonType alertElimina(Pubblicita pubblicita) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "ATTENZIONE! I token non verranno rimborsati!\n"+pubblicita.id+"|" +
                        "INIZIO: "+pubblicita.dataInizio.toString()+
                        " FINE: "+pubblicita.dataInizio.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Rimuovi pubblicita'");
        alert.showAndWait();
        return alert.getResult();
    }

    private ButtonType recapInfo(Pubblicita pubblicita) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "INIZIO: "+pubblicita.dataInizio.toString()+
                        "\nFINE: "+pubblicita.dataFine.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pubblicita'");
        alert.showAndWait();
        return alert.getResult();
    }


    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        settaTabella();
        cercaPubblicita();
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));
    }


    private void cercaPubblicita() throws SQLException {
        int i = new GestorePubblicita().getPubblicitaAttivaByNegozio(getNegozio()).size();
        if(i >= 1){
            lblPubblicita.setText("HAI UNA PUBBLICITA' ATTIVA!");
        }else
            lblPubblicita.setText("NON HAI PUBBLICITA' ATTIVE!");
    }

    private void setNegozio(Account account){
        if(account instanceof Negozio){
            this.negozio = (Negozio) account;
        }
    }

    private Negozio getNegozio(){
        return negozio;
    }


}
