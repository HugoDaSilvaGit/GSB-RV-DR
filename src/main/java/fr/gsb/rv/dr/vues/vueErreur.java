package fr.gsb.rv.dr.vues;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class vueErreur extends Alert {
    public vueErreur(String title, String header, String content) {
        super(AlertType.ERROR);
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(content);
    }
}
