package fr.gsb.rv.dr.panneaux;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauAccueil extends GridPane{

    public PanneauAccueil(){
        super();
        VBox root= new VBox();
        this.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #46ffa0, #57ffff) ; -fx-alignment: center");
        Image image = new Image("https://i.imgur.com/k1xhSgD.png");
        this.getChildren().add(new ImageView(image));
        this.getChildren().add(root);

    }

}
