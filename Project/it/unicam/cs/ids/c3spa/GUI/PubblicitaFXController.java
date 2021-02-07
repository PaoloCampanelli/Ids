package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pubblicita;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PubblicitaFXController implements FXStage{

    private ObservableList<Pubblicita> listaPubblicita;
    private Negozio negozio;
    @FXML
    private DatePicker dpInizio, dpFine;
    @FXML
    private Label lblToken, lblConta;
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

    //Da modificare poi
    public void actionSettaToken() {
        lblConta.setText("NECESSARI: 1");
    }

    public void actionAttiva() throws SQLException {
        settaPubblicita();
    }

    private void settaTabella() throws SQLException {

        List<Pubblicita> pubblicita = new GestorePubblicita().getPubblicitaAttive();
        if(pubblicita.size()>1) {
            listaPubblicita = FXCollections.observableArrayList(pubblicita);
            tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
            tbInizio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataInizio.toString()));
            tbFine.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataFine.toString()));
            tabellaPubblicita.setItems(listaPubblicita);
        }else
            tabellaPubblicita.setPlaceholder(new Label("Non ci sono pubblicita'!"));
        lblToken.setText("I TUOI TOKEN: "+getNegozio().token);
    }

    private void settaPubblicita() throws SQLException {
        Date dataInizio = Date.from(dpInizio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date dataFine = Date.from(dpFine.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        if (dataInizio.before(dataFine)) {
            Pubblicita pubblicita = new Pubblicita(Servizi.dataUtilToSql(dataInizio), Servizi.dataUtilToSql(dataFine), getNegozio());
            if(getNegozio().token==0){
                lblConta.setText("CONTATTA ADMIN!");
            }else{
                if(recapInfo(pubblicita) == ButtonType.OK){
                    new GestorePubblicita().save(pubblicita);
                    //new GestoreNegozio().save(getNegozio());
                    listaPubblicita.add(pubblicita);
                    //Attualmente toglie automaticamente un token alla volta
                    aggiornaToken();
                }
            }

        }
    }

    private ButtonType recapInfo(Pubblicita pubblicita) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "INIZIO: "+pubblicita.dataInizio.toString()+
                        "\nFINE: "+pubblicita.dataInizio.toString(), ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
    }

    public void aggiornaToken(){
        getNegozio().token -= 1;
        lblToken.setText("I TUOI TOKEN: "+getNegozio().token);
    }



    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        settaTabella();
        lblToken.setText("I TUOI TOKEN: "+getNegozio().token);
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
