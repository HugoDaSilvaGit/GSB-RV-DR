package fr.gsb.rv.dr.panneaux;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modele.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.Collections;
import java.util.List;


public class PanneauPraticiens extends StackPane {

    public static final int CRITERE_COEF_CONFIANCE = 1;
    public static final int CRITERE_COEF_NOTORIETE = 2;
    public static final int CRITERE_DATE_VISITE = 3;

    private int critereTri = CRITERE_COEF_CONFIANCE;
    RadioButton rbCoefConfiance = new RadioButton("Confiance");
    RadioButton rbCoefNotoriete = new RadioButton("Notoriété");
    RadioButton rbDateVisite = new RadioButton("Date Visite");
    Label label = new Label("Selectionner un critère de tri : ");
    GridPane grid = new GridPane();
    VBox vboxLabel = new VBox();
    VBox vboxTab = new VBox();
    VBox root = new VBox();
    ToggleGroup toggle = new ToggleGroup();
    TableView<Praticien> tabPraticien = new TableView<>();
    TableColumn<Praticien, Integer> numCol = new TableColumn<>("Numéro");
    TableColumn<Praticien, String> nomCol = new TableColumn<>("Nom");
    TableColumn<Praticien, String> villeCol = new TableColumn<>("Ville");


    public PanneauPraticiens() throws ConnexionException {
        super();

        this.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #46ffa0, #57ffff)");
        numCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        villeCol.setCellValueFactory(new PropertyValueFactory<>("ville"));
        List<Praticien> PraticienHesitants = ModeleGsbRv.getPraticiensHesitants();
        ObservableList<Praticien> PraticiensObservable = FXCollections.observableArrayList(PraticienHesitants);
        for (Praticien praticien : PraticiensObservable){
            tabPraticien.getItems().add(praticien);
        }
        tabPraticien.getColumns().addAll(numCol, nomCol, villeCol);
        tabPraticien.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rbCoefConfiance.setToggleGroup(toggle);
        rbCoefNotoriete.setToggleGroup(toggle);
        rbDateVisite.setToggleGroup(toggle);
        rbCoefConfiance.setSelected(true);
        grid.setHgap(10);
        grid.setVgap(10);
        vboxLabel.setPadding(new Insets(10,10,10,0));
        vboxLabel.getChildren().add(label);
        grid.setPadding(new Insets(10,10,10,0));
        grid.add(rbCoefConfiance, 0,0);
        grid.add(rbCoefNotoriete, 1,0);
        grid.add(rbDateVisite, 2,0);
        vboxTab.getChildren().add(tabPraticien);
        root.getChildren().addAll(vboxLabel, grid, vboxTab);
        this.getChildren().add(root);
        rbCoefNotoriete.setOnAction((ActionEvent )->{
            critereTri = CRITERE_COEF_NOTORIETE;
            try {
                rafraichir();
            } catch (ConnexionException e) {
                e.printStackTrace();
            }
        });
        rbCoefConfiance.setOnAction((ActionEvent )->{
            critereTri = CRITERE_COEF_CONFIANCE;
            try {
                rafraichir();
            } catch (ConnexionException e) {
                e.printStackTrace();
            }
        });
        rbDateVisite.setOnAction((ActionEvent )->{
            critereTri = CRITERE_DATE_VISITE;
            try {
                rafraichir();
            } catch (ConnexionException e) {
                e.printStackTrace();
            }
        });
    }

    public int getCritereTri() {
        return critereTri;
    }

    public void rafraichir() throws ConnexionException {
        List<Praticien> PraticienHesitants = ModeleGsbRv.getPraticiensHesitants();
        ObservableList<Praticien> PraticiensObservable = FXCollections.observableArrayList(PraticienHesitants);
        if (critereTri == CRITERE_COEF_NOTORIETE){
            PraticiensObservable.sort(new ComparateurCoefNotoriete());
            Collections.reverse(PraticiensObservable);
            tabPraticien.setItems(PraticiensObservable);

        }else if(critereTri == CRITERE_DATE_VISITE) {
            PraticiensObservable.sort(new ComparateurDateVisite());
            Collections.reverse(PraticiensObservable);
            tabPraticien.setItems(PraticiensObservable);

        }else{
            PraticiensObservable.sort(new ComparateurCoefConfiance());
            tabPraticien.setItems(PraticiensObservable);
        }
    }



}
