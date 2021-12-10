package fr.gsb.rv.dr.panneaux;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauRapports extends GridPane {

    public PanneauRapports(){
        super();
        VBox root= new VBox();
        Label label = new Label("Rapports");
        this.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #46ffa0, #57ffff)");
        root.setSpacing(10);
        root.setPadding(new Insets(15,20, 10,10));
        root.getChildren().add(label);
        this.getChildren().add(root);

    }

}
