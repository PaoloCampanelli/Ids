package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Sconto;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreSconto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class ISconti implements FXStage {

    private ObservableList<String> selezionaData = FXCollections.observableArrayList("OGGI", "DIVERSA");
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
    private TableColumn<Sconto, String> tbSTipo;
    @FXML
    private TableColumn<Sconto, String> tbSCategorie;
    @FXML
    private TableColumn<Sconto, String> tbSInizio;
    @FXML
    private TableColumn<Sconto, String> tbSFine;
    @FXML
    private TableColumn<Sconto, String> tbID;
    @FXML
    private TextField txtTipo, txtIDCategoria, txtIDSconto;
    @FXML
    private DatePicker dpInizio, dpFine;
    @FXML
    private Button btnConferma;
    @FXML
    private ComboBox sceltaData;
    @FXML
    private HBox hboxNascosta;
    @FXML
    private Label lblErrore, lblErrore1;
    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {
        sceltaData.setItems(selezionaData);
        sceltaData.setValue("OGGI");
        dpInizio.setDisable(true);
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));
    }

    public void settaCategoria(Negozio negozio) {
        List<CategoriaMerceologica> categoria = negozio.categorie;
        categorie = FXCollections.observableArrayList(categoria);
        categorie.removeIf(c -> c.idCategoria == 0);
        tbCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().idCategoria)));
        tbCCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().nome));
        tabellaCategorie.setItems(categorie);
        tabellaCategorie.setPlaceholder(new Label("Non hai categorie attive!"));
    }

    private void setTabellaSconti(Negozio negozio) throws SQLException {
        List<Sconto> listaSconti = new GestoreSconto().getScontiAttiviByNegozio(negozio);
        sconti = FXCollections.observableArrayList(listaSconti);
        tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tbSInizio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataInizio.toString()));
        tbSFine.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataFine.toString()));
        tbSTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().tipo));
        tbSCategorie.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categoriaMerceologica.nome));
        tabellaSconti.setItems(sconti);
        tabellaSconti.setPlaceholder(new Label(("Non hai sconti attivi!")));
    }

    public void actionScopri() {
        dpInizio.setDisable(!sceltaData.getValue().equals("DIVERSA"));
    }

    public void actionConferma() throws SQLException {
        try{
            int id = Integer.parseInt(txtIDCategoria.getText());
            inserimentoInfo(txtTipo.getText().toUpperCase(),id);
        }catch (NumberFormatException e){

        }

    }

    public void actionAnnulla(ActionEvent actionEvent) throws SQLException {
        int i = Integer.parseInt(txtIDSconto.getText());
        annullaSconto(i);
    }

    private void annullaSconto(int id) throws SQLException {
        GestoreSconto gs = new GestoreSconto();
        if(sconti.stream().anyMatch(s -> s.id == id)){
            gs.delete(id);
        }
        setTabellaSconti(getNegozio());
    }

    private void inserimentoInfo(String tipo, int id) throws SQLException {
        lblErrore1.setText("");
        if (!tipo.isBlank()) {
            CategoriaMerceologica categoriaMerceologica = prendiCategoria(id);
            if (categoriaMerceologica != null) {
                Date fine = Date.from(dpFine.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                Date inizio = new Date();
                if (sceltaData.getValue().equals("DIVERSA")) {
                    inizio = Date.from(dpInizio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                }else if(sceltaData.getValue().equals("OGGI")) {
                    dpInizio.setValue(LocalDate.now());
                }
                if (dpInizio.getValue().isBefore(dpFine.getValue())) {
                    Sconto sconto = new Sconto(tipo, Servizi.dataUtilToSql(inizio), Servizi.dataUtilToSql(fine), getNegozio(), categoriaMerceologica);
                    confermaSconto(sconto);
                }else
                    lblErrore1.setText("Date non valide");
            }else
                lblErrore1.setText("Categoria non presente");
        }else
            lblErrore1.setText("Tipologia non valida");
    }


    private void confermaSconto(Sconto sconto) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "TIPO: " + sconto.tipo +
                        "\nINZIO: " + sconto.dataInizio.toString() +
                        "\nFINE: " + sconto.dataFine.toString() +
                        "\nCATEGORIA: " + sconto.categoriaMerceologica.nome
                , ButtonType.OK, ButtonType.NO);
        alert.setTitle("Conferma pacco");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK) {
            new GestoreNegozio().creaSconto(sconto, getNegozio());
            setTabellaSconti(getNegozio());
        }
    }


    private CategoriaMerceologica prendiCategoria(int idCategoria) {
        return negozio.categorie.stream().filter(c -> c.idCategoria == idCategoria).findAny().orElse(null);
    }


    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        settaCategoria(negozio);
        setTabellaSconti(negozio);
    }

    public Negozio getNegozio() {
        return negozio;
    }

    public void setNegozio(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        }
    }

}
