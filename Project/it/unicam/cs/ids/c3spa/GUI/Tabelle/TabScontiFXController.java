package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreSconto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class TabScontiFXController implements FXTabella{

    private ObservableList<String> selezionaData = FXCollections.observableArrayList("OGGI","DIVERSA");
    private ObservableList<Sconto> sconti;
    private ObservableList<CategoriaMerceologica> categorie;
    private Negozio negozio;

    @FXML
    private TableView tabellaCategorie;
    @FXML
    private TableColumn<CategoriaMerceologica, String> tbCID;
    @FXML
    private TableColumn<CategoriaMerceologica, String> tbCCategoria;

    @FXML
    private TableView tabellaSconti;
    @FXML
    private TableColumn<Sconto, String> tbSID;
    @FXML
    private TableColumn<Sconto, String> tbSTipo;
    @FXML
    private TableColumn<Sconto, String> tbSCategorie;
    @FXML
    private TableColumn<Sconto, String> tbSInizio;
    @FXML
    private TableColumn<Sconto, String> tbSFine;
    @FXML
    private TextField txtTipo, txtIDCategoria;
    @FXML
    private DatePicker dpInizio, dpFine;
    @FXML
    private Button btnConferma;
    @FXML
    private ComboBox sceltaData;
    @FXML
    private HBox hboxNascosta;
    @FXML
    private Label lblError;

    @FXML
    public void initialize(){
        sceltaData.setItems(selezionaData);
        sceltaData.setValue("OGGI");
        hboxNascosta.setDisable(true);
    }

    public void settaCategoria(Negozio negozio){
        List<CategoriaMerceologica> categoria = negozio.categorie;
        categorie = FXCollections.observableArrayList(categoria);
        categorie.removeIf(c -> c.idCategoria == 0);
        tbCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().idCategoria)));
        tbCCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().nome));
        tabellaCategorie.setItems(categorie);
        tabellaCategorie.setPlaceholder(new Label("Non hai categorie attive!"));
    }

    private void setTabellaSconti(Negozio negozio) throws SQLException {
       List<Sconto> listaSconti = new GestoreSconto().getAll();
       sconti = FXCollections.observableArrayList(listaSconti);
       tbSID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
       tbSInizio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataInizio.toString()));
       tbSFine.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataFine.toString()));
       tbSTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().tipo));
       tbSCategorie.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categoriaMerceologica.nome));
       tabellaSconti.setItems(sconti);
       tabellaSconti.setPlaceholder(new Label(("Non hai sconti attivi!")));
    }


    public void actionScopri(ActionEvent actionEvent) {
        if(sceltaData.getValue().equals("DIVERSA")){
            hboxNascosta.setDisable(false);
        }else
            hboxNascosta.setDisable(true);
    }

    public void actionConferma(ActionEvent actionEvent) throws SQLException {
        inserimentoInfo();
    }

    private void inserimentoInfo() throws SQLException {
        String tipo = txtTipo.getText().toUpperCase();
        LocalDate ldFine = dpFine.getValue();
        try {
            if(!tipo.isBlank()) {
                int id = Integer.parseInt(txtIDCategoria.getText());
                CategoriaMerceologica categoriaMerceologica = prendiCategoria(id);
                Date fine = Date.from(ldFine.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                if (categoriaMerceologica != null) {
                    Date inizio;
                    if (sceltaData.getValue().equals("DIVERSA")) {
                        LocalDate ldInizio = dpInizio.getValue();
                        inizio = Date.from(ldInizio.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                        if(ldInizio.isBefore(ldFine)){
                            Sconto sconto = new Sconto(tipo, inizio, fine, negozio, categoriaMerceologica);
                            if(recapInfo(sconto) == ButtonType.OK) {
                                sconti.add(sconto);
                                new GestoreSconto().save(sconto);
                            }
                        }
                    } else
                        inizio = Date.from(Instant.now());
                    if (checkData(ldFine)) {
                        Sconto sconto = new Sconto(tipo, inizio, fine, negozio, categoriaMerceologica);
                        if(recapInfo(sconto) == ButtonType.OK){
                            sconti.add(sconto);
                            new GestoreSconto().save(sconto);
                    }
                }
                    }else
                    lblError.setText(id + " non presente");
                }
        }catch (NumberFormatException | SQLException e){
            e.getMessage();
        }
    }



    private ButtonType recapInfo(Sconto sconto) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
        "TIPO: "+sconto.tipo +
                "\nINZIO: " +sconto.dataInizio.toString()+
                "\nFINE: " +sconto.dataFine.toString()+
                "\nCATEGORIA: "+sconto.categoriaMerceologica.nome
                , ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        return alert.getResult();
    }

    private boolean checkData(LocalDate fine) {
            return !fine.isBefore(LocalDate.now());
    }


    private CategoriaMerceologica prendiCategoria(int idCategoria) {
        return negozio.categorie.stream().filter(c -> c.idCategoria==idCategoria).findAny().orElse(null);
    }


    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        settaCategoria(negozio);
        setTabellaSconti(negozio);
    }

    @Override
    public void initData(Account account, String citta, String categoria) throws SQLException {

    }

    public void setNegozio(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        }
    }

    public Negozio getNegozio() {
        return negozio;
    }
}
