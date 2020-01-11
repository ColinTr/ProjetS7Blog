package vue.javafxobservers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ObsMessage implements Initializable {
    @FXML private Text texteNom;
    @FXML private Text texteDate;
    @FXML private Label texteMessage;
    @FXML private Label texteTitre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void definirMessage(String nom, String date, String titre, String message){
        texteNom.setText(nom);
        texteDate.setText(date);
        texteTitre.setText(titre);
        texteMessage.setText(message);
    }
}
