package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.GUI.FXStage;
import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class TabCategoriaNegoziFXController implements FXStage {

    private Negozio negozio;
    private ObservableList<CategoriaMerceologica> lcm;
    @FXML
    private Button btnInserisci, btnRimuovi;
    @FXML
    private Label lblCategoria, lblID;
    @FXML
    private TextField txtCategoria, txtID;
    @FXML
    private TableView<CategoriaMerceologica> tabellaCategoria;
    @FXML
    private TableColumn<CategoriaMerceologica, String> tbID;
    @FXML
    private TableColumn<CategoriaMerceologica, String> tbNome;
    public TabCategoriaNegoziFXController() {
    }

    public void actionInserisci() throws SQLException {

       aggiungiCategoria(txtCategoria.getText());
    }

    public void actionRimuovi(){
        rimuoviCategoria(Integer.parseInt(txtID.getText()));
    }

    /**
     * Setta un ObservableList delle categorie del negozio
     * @param negozio
     *          negozio loggato
     */
    public void setta(Negozio negozio) {
        tabellaCategoria.setPlaceholder(new Label("Non hai categorie attive!"));
        List<CategoriaMerceologica> categoria = negozio.categorie;
        lcm = FXCollections.observableArrayList(categoria);
        lcm.removeIf(c -> c.idCategoria == 0);
        tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().idCategoria)));
        tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().nome));
        tabellaCategoria.setItems(lcm);
    }

    /**
     * Inizializza i dati dell'account
     * @param account account loggato
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setNegozio(account);
        setta(negozio);
    }

    /**
     * Aggiunge la categoria
     * @param nomeCategoria
     *          nome della categoria da aggiungere
     * @throws SQLException
     */
    private void aggiungiCategoria(String nomeCategoria) throws SQLException {
        CategoriaMerceologica categoria = new CategoriaMerceologica(nomeCategoria.toUpperCase());
        if (!(nomeCategoria.isBlank())) {
            if (negozio.aggiungiCategoria(categoria)) {
                lcm.add(categoria);
                new GestoreCategoriaMerceologica().save(categoria);
                new GestoreNegozio().save(negozio);
            }
        } else
            lblCategoria.setText("Categoria gia' presente");
        txtCategoria.clear();
    }

    /**
     * Rimuove la categoria
     * @param id
     *      id della categoria
     */
    private void rimuoviCategoria(int id){
        try {
            CategoriaMerceologica categoria = prendiCategoria(id);
            if (negozio.rimuoviCategoria(categoria)) {
                lcm.remove(categoria);
                new GestoreCategoriaMerceologica().delete(id);
            } else
                lblID.setText("ID non presente");
            txtCategoria.clear();
        } catch (NumberFormatException | SQLException e) {
            lblID.setText("ID non valido");
        }
    }

    /**
     * Prende la categoria ricercata
     * @param idCategoria
     *          id della categoria
     * @return categoria
     */
    private CategoriaMerceologica prendiCategoria(int idCategoria) {
        return negozio.categorie.stream().filter(c -> c.idCategoria == idCategoria).findAny().get();
    }



    /**
     * @return negozio
     */
    public Negozio getNegozio() {
        return negozio;
    }

    /**
     * Setta l'account se e' un negozio
     * @param account
     *          account passato
     */
    public void setNegozio(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        }
    }

}
