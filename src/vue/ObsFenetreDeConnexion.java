package vue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreDeConnexion implements Initializable {

    @FXML JFXTextField fieldNomDeCompte;
    @FXML JFXPasswordField fieldMotDePasse;
    @FXML Button boutonOptions;
    @FXML JFXButton boutonConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boutonConnection.setOnAction(event -> {

        });

        boutonOptions.setOnAction(event -> {

        });
    }
}