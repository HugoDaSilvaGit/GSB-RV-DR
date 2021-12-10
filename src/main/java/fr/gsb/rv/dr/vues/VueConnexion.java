package fr.gsb.rv.dr.vues;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

public class VueConnexion extends Dialog<Pair<String,String>> {
    public VueConnexion(){
        super();
        this.setTitle("Authentification");
        this.setHeaderText("Saisir vos données de connexion");
        Label LabelMatricule = new Label("Matricule : ");
        TextField InputMatricule = new TextField();
        Label LabelMdp = new Label("Mot de Passe : ");
        PasswordField InputMdp = new PasswordField();
        GridPane gridPane = new GridPane();
        gridPane.add(LabelMatricule, 0,1);
        gridPane.add(InputMatricule, 1,1);
        gridPane.add(LabelMdp, 0, 3);
        gridPane.add(InputMdp, 1,3);
        this.getDialogPane().setContent(gridPane);
        ButtonType btnOK = new ButtonType("Se Connecter" , ButtonBar.ButtonData.OK_DONE);
        ButtonType btnClose = new ButtonType("Annuler" , ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(btnOK, btnClose);
        this.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(ButtonType buttonType) {
                if ( buttonType == btnOK ){
                    return new Pair<String, String>(InputMatricule.getText(), InputMdp.getText());
                }
                return null;
            }
        });
    }
}
