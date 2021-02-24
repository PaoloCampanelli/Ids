package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXStage;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.gestori.GestorePubblicita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class TabNegoziFXController implements FXStage {

    private ObservableList<Negozio> ln;

    @FXML
    private TableView<Negozio> tabellaNegozi;
    @FXML
    private TableColumn<Negozio, String> tbNome;
    @FXML
    private TableColumn<Negozio, String> tbNumero;
    @FXML
    private TableColumn<Negozio, String> tbVia;

    @FXML
    public void initialize() throws SQLException {
        List<Negozio> negozi = new GestorePubblicita().OrderByPubblicita(new GestoreNegozio().getAll(), new GestorePubblicita().getNegoziConPubblicitaAttiva());
        ln = FXCollections.observableArrayList(negozi);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().denominazione));
        tbNumero.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().telefono));
        tbVia.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
        tabellaNegozi.setItems(ln);
        tabellaNegozi.setPlaceholder(new Label("C3 non contiene ancora negozi!"));
    }

    @Override
    public void initData(Account account) throws SQLException {
    }


}
