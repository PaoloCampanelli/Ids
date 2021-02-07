package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class TabGestioneFXController implements FXController {



    private ObservableList<Negozio> listaNegozi;
    private ObservableList<Cliente> listaClienti;
    private ObservableList<Corriere> listaCorrieri;

    @FXML
    TextField txtID, txtIDN, txtIDN1, txtIDCC, txtToken;
    @FXML
    Label lblCliente, lblCorriere, lblNegozio, lblNome, lblToken;

    @FXML
    private TableView<Negozio> tabellaNegozi;
    @FXML
    private TableColumn<Negozio, String> tbNID;
    @FXML
    private TableColumn<Negozio, String> tbNMail;
    @FXML
    private TableColumn<Negozio, String> tbToken;

    @FXML
    private TableView<Cliente> tabellaClienti;
    @FXML
    private TableColumn<Cliente, String> tbCID;
    @FXML
    private TableColumn<Cliente, String> tbCMail;

    @FXML
    private TableView<Corriere> tabellaCorrieri;
    @FXML
    private TableColumn<Corriere, String> tbCCID;
    @FXML
    private TableColumn<Corriere, String> tbCCMail;

    private void settaNegozi() throws SQLException {
        List<Negozio> negozi = new GestoreNegozio().getAll();
        listaNegozi = FXCollections.observableArrayList(negozi);
        listaNegozi.removeIf(c -> c.id == 0);
        tbNMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbToken.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().token)));
        tbNID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaNegozi.setItems(listaNegozi);
    }

    private void settaClienti() throws SQLException {
        List<Cliente> clienti = new GestoreCliente().getAll();
        listaClienti = FXCollections.observableArrayList(clienti);
        listaClienti.removeIf(c -> c.id == 0);
        tbCMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaClienti.setItems(listaClienti);
    }

    private void settaCorrieri() throws SQLException {
        List<Corriere> corrieri = new GestoreCorriere().getAll();
        listaCorrieri = FXCollections.observableArrayList(corrieri);
        listaCorrieri.removeIf(c -> c.id == 0);
        tbCCMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbCCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaCorrieri.setItems(listaCorrieri);
    }


    public void actionRimuoviCliente(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtID.getText());
            cancellaAccount("CLIENTE", id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actionRimuoviCorriere(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtIDCC.getText());
            cancellaAccount("CORRIERE", id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actionRimuoviNegozio(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtIDN.getText());
            cancellaAccount("NEGOZIO", id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actionTokenAggiungi() {
        try {
            int id = Integer.parseInt(txtIDN1.getText());
            int token = Integer.parseInt(txtToken.getText());
            controllaInfo("AGGIUNGI", id, token);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void actionTokenRimuovi() {
        try {
            int id = Integer.parseInt(txtIDN1.getText());
            int token = Integer.parseInt(txtToken.getText());
            controllaInfo("RIMUOVI", id, token);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void cancellaAccount(String tipologia, int id) throws SQLException {
        lblCliente.setText("");
        lblNegozio.setText("");
        lblCorriere.setText("");
        switch(tipologia) {
            case "CLIENTE": {
                GestoreCliente gc = new GestoreCliente();
                Cliente cliente = gc.getById(id);
                if(cercaCliente(id)){
                    if (alertEliminazione(cliente) == ButtonType.OK) {
                        gc.delete(id);
                        settaClienti();
                    } else {
                        txtIDN.clear();
                        lblCliente.setText("");
                    }
                }else
                    lblCliente.setText("ID NON TROVATO");
            }
            break;
           case "NEGOZIO":{
               GestoreNegozio gn = new GestoreNegozio();
               Negozio negozio = gn.getById(id);
               if(cercaNegozio(id)){
                   if (alertEliminazione(negozio) == ButtonType.OK) {
                       gn.delete(id);
                   } else {
                       txtIDN.clear();
                       lblNegozio.setText("");
                       settaNegozi();
                   }
                }else
                   lblNegozio.setText("ID NON TROVATO");
               }
               break;
           case "CORRIERE": {
               GestoreCorriere gcc = new GestoreCorriere();
               Corriere corriere = gcc.getById(id);
               if (cercaCorriere(id)) {
                   if (alertEliminazione(corriere) == ButtonType.OK) {
                       gcc.delete(id);
                   } else {
                       txtIDN.clear();
                       lblCorriere.setText("");
                       settaCorrieri();
                   }
               } else
                   lblCorriere.setText("ID NON TROVATO");
           }
               break;
        }
    }


    private ButtonType alertEliminazione(Account account) {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Account \n"+account.id+"\n"+account.eMail+"\n Vuoi confermare?", ButtonType.OK, ButtonType.NO);
        alert.setTitle("RIMOZIONE!");
        alert.showAndWait();
        return alert.getResult();
    }

    @Override
    public void initData(Account account) throws SQLException {
        settaClienti();
        settaCorrieri();
        settaNegozi();
    }


    private void controllaInfo(String tipo, int id, int token) throws SQLException {
        lblToken.setText("");
        lblNome.setText("");
        GestoreNegozio gn = new GestoreNegozio();
        if(cercaNegozio(id)){
            Negozio negozio = gn.getById(id);
            if(tipo.equals("RIMUOVI")){
                if(token <= negozio.token){
                    if(alertToken(tipo, negozio) == ButtonType.OK){
                        negozio.token-=token;
                        gn.save(negozio);
                    }else {
                        txtIDN1.clear();
                        txtToken.clear();
                    }
                }else
                    lblToken.setText("Token non validi");
            }else if(tipo.equals("AGGIUNGI")){
                if(alertToken(tipo, negozio) == ButtonType.OK){
                    negozio.token+=token;
                    gn.save(negozio);
                }else {
                    txtIDN1.clear();
                    txtToken.clear();
                }
            }
        }else
            lblNome.setText("Non trovato!");
        settaNegozi();
    }

    private ButtonType alertToken(String tipo, Negozio negozio) {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Negozio: "+negozio.denominazione+"\n"+tipo+":" +txtToken.getText()+"Vuoi confermare?", ButtonType.OK, ButtonType.NO);
        alert.setTitle("TOKEN!");
        alert.showAndWait();
        return alert.getResult();
    }

    private boolean cercaCliente(int idRicerca){
        return listaClienti.stream().anyMatch(c -> c.id == idRicerca);
    }

    private boolean cercaNegozio(int idRicerca){
        return listaNegozi.stream().anyMatch(c -> c.id == idRicerca);
    }

    private boolean cercaCorriere(int idRicerca){
        return listaCorrieri.stream().anyMatch(c -> c.id == idRicerca);
    }

}
