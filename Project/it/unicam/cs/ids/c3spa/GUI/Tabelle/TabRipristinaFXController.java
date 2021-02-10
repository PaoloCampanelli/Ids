package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXController;
import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class TabRipristinaFXController implements FXController {

    private Amministratore amministratore;

    private ObservableList<Negozio> listaNegozi;
    private ObservableList<Cliente> listaClienti;
    private ObservableList<Corriere> listaCorrieri;

    @FXML
    TextField txtID, txtIDN, txtIDCC;
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

    private void settaNegozi(Amministratore amministratore) throws SQLException {
        List<Negozio> negozi = new GestoreAmministratore().getNegoziCancellati(amministratore);
        listaNegozi = FXCollections.observableArrayList(negozi);
        tbNMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbToken.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().token)));
        tbNID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaNegozi.setItems(listaNegozi);
    }

    private void settaClienti(Amministratore amministratore) throws SQLException {
        List<Cliente> clienti = new GestoreAmministratore().getClientiCancellati(amministratore);
        listaClienti = FXCollections.observableArrayList(clienti);
        tbCMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaClienti.setItems(listaClienti);
    }

    private void settaCorrieri(Amministratore amministratore) throws SQLException {
        List<Corriere> corrieri = new GestoreAmministratore().getCorrieriCancellati(amministratore);
        listaCorrieri = FXCollections.observableArrayList(corrieri);
        tbCCMail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
        tbCCID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
        tabellaCorrieri.setItems(listaCorrieri);
    }

    @Override
    public void initData(Account account) throws SQLException {
        setAmministratore(account);
        settaClienti(getAdmin());
        settaCorrieri(getAdmin());
        settaNegozi(getAdmin());
    }

    private void setAmministratore(Account account){
        if(account instanceof Amministratore){
            this.amministratore= (Amministratore) account;
        }
    }

    private Amministratore getAdmin(){
        return amministratore;
    }

    public void actionRipristinaCliente() throws SQLException {
        lblCliente.setText("");
        ripristinaCliente(txtID.getText().toUpperCase());
    }

    public void actionRipristinaCorriere() throws SQLException {
        lblCorriere.setText("");
        ripristinaCorriere(txtIDCC.getText().toUpperCase());
    }

    public void actionRipristinaNegozio() throws SQLException {
        lblCorriere.setText("");
        ripristinaNegozio(txtIDN.getText().toUpperCase());
    }

    private void ripristinaCliente(String email) throws SQLException {
        GestoreCliente gc = new GestoreCliente();
       if(cercaCliente(email)){
           Cliente cliente = gc.getByEMail(email);
            if(alertRipristina(cliente) == ButtonType.OK){
                new GestoreAmministratore().ripristinaCliente(cliente);
                gc.save(cliente);
                settaClienti(getAdmin());
            }
       }else
           lblCliente.setText("NON TROVATO!");
    }

    private void ripristinaNegozio(String email) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        if(cercaNegozio(email)){
            Negozio negozio = gn.getByEMail(email);
            if(alertRipristina(negozio) == ButtonType.OK){
                new GestoreAmministratore().ripristinaNegozio(negozio);
                gn.save(negozio);
                settaNegozi(getAdmin());
            }
        }else
            lblNegozio.setText("NON TROVATO!");
    }

    private void ripristinaCorriere(String email) throws SQLException {
        GestoreCorriere gc = new GestoreCorriere();
        if(cercaCorriere(email)){
            Corriere corriere = gc.getByEMail(email);
            if(alertRipristina(corriere) == ButtonType.OK){
                new GestoreAmministratore().ripristinaCorriere(corriere);
                gc.save(corriere);
                settaCorrieri(getAdmin());
            }
        }else
         lblCorriere.setText("NON TROVATO!");
    }

    private boolean cercaCliente(String email) {
        return listaClienti.stream().anyMatch(c -> c.eMail.equals(email));
    }

    private boolean cercaNegozio(String email){
        return listaNegozi.stream().anyMatch(c -> c.eMail.equals(email));
    }

    private boolean cercaCorriere(String email){
        return listaCorrieri.stream().anyMatch(c -> c.eMail.equals(email));
    }

    private ButtonType alertRipristina(Account account) {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Account " +account.id+
                        "\n"+account.eMail+
                        "\nVuoi confermare?", ButtonType.OK, ButtonType.NO);
        alert.setTitle("RIPRISTINA!");
        alert.showAndWait();
        return alert.getResult();
    }
}
