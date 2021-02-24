package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXStage;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Sconto;
import it.unicam.cs.ids.c3spa.astratto.Account;
import it.unicam.cs.ids.c3spa.gestori.GestoreSconto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class TabScontiProvinciaFXController implements FXStage {

    private Cliente cliente;
    private ObservableList<Sconto> ls;
    @FXML
    private TableView<Sconto> tabellaSconti;
    @FXML
    private TableColumn<Sconto, String> tbNome;
    @FXML
    private TableColumn<Sconto, String> tbIndirizzo;
    @FXML
    private TableColumn<Sconto, String> tbTipo;
    @FXML
    private TableColumn<Sconto, String> tbCategoria;
    @FXML
    private TableColumn<Sconto, String> tbFine;

    public TabScontiProvinciaFXController() {
    }

    public void settaTabella(Cliente cliente) throws SQLException {
        List<Sconto> sconti = new GestoreSconto().getScontiAttiviByProvincia(cliente.indirizzo.provincia);
        ls = FXCollections.observableArrayList(sconti);
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().negozio.denominazione));
        tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().negozio.indirizzo.toString()));
        tbTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().tipo));
        tbCategoria.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().categoriaMerceologica.nome));
        tbFine.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataFine.toString()));
        tabellaSconti.setItems(ls);
        tabellaSconti.setPlaceholder(new Label("C3 non contiene sconti a "+cliente.indirizzo.provincia));
    }

    @Override
    public void initData(Account account) throws SQLException {
        setCliente(account);
        settaTabella(getCliente());
    }

    private void setCliente(Account account){
        this.cliente = (Cliente) account;

    }
    private Cliente getCliente(){
        return  cliente;
    }
}
