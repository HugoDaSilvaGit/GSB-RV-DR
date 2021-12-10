package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modele.ModeleGsbRv;
import fr.gsb.rv.dr.panneaux.PanneauAccueil;
import fr.gsb.rv.dr.panneaux.PanneauPraticiens;
import fr.gsb.rv.dr.panneaux.PanneauRapports;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.vues.VueConnexion;
import fr.gsb.rv.dr.vues.vueErreur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Appli extends Application {
    @Override
    public void start(Stage stage) throws IOException, ConnexionException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 350);
        stage.setTitle("GSB-RV-DR");

        MenuBar menu = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        Menu menuRapport = new Menu("Rapports");
        Menu menuPraticien = new Menu("Praticien");
        MenuItem Hesitant = new MenuItem("Hésitant");

        MenuItem SeConnecter  = new MenuItem("Se Connecter");
        MenuItem SeDeconnecter = new MenuItem("Se Deconnecter");
        SeDeconnecter.setDisable(true);
        MenuItem Quitter = new MenuItem("Quitter");
        MenuItem Consulter = new MenuItem("Consulter");
        menuRapport.getItems().add(Consulter);
        menuPraticien.getItems().add(Hesitant);
        menuFichier.getItems().addAll(SeConnecter, SeDeconnecter, Quitter);
        menu.getMenus().add(menuFichier);
        menu.getMenus().add(menuRapport);
        menuRapport.setDisable(true);
        menu.getMenus().add(menuPraticien);
        menuPraticien.setDisable(true);


        Alert ModalQuitter = new Alert(Alert.AlertType.CONFIRMATION);
        ModalQuitter.setTitle("Quitter");
        ModalQuitter.setHeaderText("Demande de confirmation");
        ModalQuitter.setContentText("Voulez-vous quitter l'application ? ");
        ModalQuitter.getButtonTypes().remove(0);
        ModalQuitter.getButtonTypes().remove(0);

        ButtonType btnOui = new ButtonType("Oui");
        ButtonType btnNon = new ButtonType("Non");
        ModalQuitter.getButtonTypes().addAll(btnOui, btnNon);
        PanneauAccueil vueAccueil = new PanneauAccueil();
        PanneauRapports vueRapport = new PanneauRapports();
        PanneauPraticiens vuePraticiens = new PanneauPraticiens();

        StackPane pile = new StackPane();
        pile.getChildren().add(vueRapport);
        pile.getChildren().add(vueAccueil);
        pile.getChildren().add(vuePraticiens);
        root.setCenter(pile);
        vueAccueil.toFront();

        SeConnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        VueConnexion vue = new VueConnexion();
                        Optional<Pair<String,String>> reponse = vue.showAndWait();
                        if (reponse.isPresent()){
                            try {
                                Visiteur delegue = ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
                                if (delegue != null){
                                    Session.ouvrir(delegue);
                                    SeDeconnecter.setDisable(false);
                                    SeConnecter.setDisable(true);
                                    menuRapport.setDisable(false);
                                    menuPraticien.setDisable(false);
                                    stage.setTitle("GSB-RV-DR" + " (" +Session.getSession().getVisiteur().getPrenom() + " " + Session.getSession().getVisiteur().getNom()+")");
                                }else{
                                    vueErreur ErreurVue = new vueErreur("Erreur",
                                            "Connexion impossible",
                                            "Matricule ou mot de passe incorrect."
                                    );

                                    ErreurVue.showAndWait();
                                }

                            } catch (ConnexionException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );

        SeDeconnecter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        SeDeconnecter.setDisable(true);
                        SeConnecter.setDisable(false);
                        menuRapport.setDisable(true);
                        menuPraticien.setDisable(true);
                        stage.setTitle("GSB-RV-DR");
                        Session.fermer();
                        vueAccueil.toFront();
                    }
                }
        );


        Consulter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        vueRapport.toFront();
                    }
                }
        );

        Hesitant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vuePraticiens.toFront();
            }
        });

        Quitter.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Optional<ButtonType> reponse = ModalQuitter.showAndWait();
                        if(reponse.get() == btnOui) {
                            Platform.exit();
                        }
                    }
                }
        );


        root.setTop(menu);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}