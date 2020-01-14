package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controleur.ControleurDonnees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modele.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ObsFenetreModifierMessage implements Initializable {
    @FXML private GridPane gridPaneImages;
    @FXML private TabPane tabPanePrincipal;
    @FXML private JFXTextField fieldTitre;
    @FXML private JFXTextArea messageArea;
    @FXML private JFXTextField fieldMotsCles;
    @FXML private JFXButton boutonAjouterImage;
    @FXML private TextField fieldAdresseImage;
    @FXML private JFXListView<String> imagesListView;
    private static ObservableList<String> imagesObservableList = FXCollections.observableArrayList();
    @FXML private JFXButton boutonAjouterLien;
    private Message messageAffiche;
    @FXML private TextField fieldTexteLien;
    @FXML private TextField fieldAdresseLien;
    @FXML private JFXListView<String[]> liensListView;
    private static ObservableList<String[]> liensObservableList = FXCollections.observableArrayList();
    @FXML private JFXButton boutonPoster;
    @FXML private JFXButton boutonAnnuler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //On pré-remplit donc les données à partir du message à afficher :
        fieldTitre.setText(messageAffiche.getTitre());
        messageArea.setText(messageAffiche.getTexte());

        for(MotCle motCle : messageAffiche.getMotCles()){
            fieldMotsCles.setText(fieldMotsCles.getText() + " " + motCle.getMotCle());
        }

        for(Image image : messageAffiche.getImages()){
            imagesObservableList.add(image.getAdresseImage());
        }

        for(Lien lien : messageAffiche.getLiens()){
            liensObservableList.add(new String[]{lien.getTexteLien(), lien.getAdresseLien()});
        }

        imagesListView.setCellFactory(param -> new ObsFenetreCreerMessage.XCellString());
        imagesListView.setItems(imagesObservableList);

        liensListView.setCellFactory(param -> new ObsFenetreCreerMessage.XCellStringTable());
        liensListView.setItems(liensObservableList);

        boutonAjouterLien.setOnAction(event -> {
            if(!fieldTexteLien.getText().isEmpty() && !fieldAdresseLien.getText().isEmpty()){
                liensObservableList.add(new String[]{fieldTexteLien.getText(), fieldAdresseLien.getText()});
                fieldTexteLien.setText(null);
                fieldAdresseLien.setText(null);
            }
            event.consume();
        });

        boutonAjouterImage.setOnAction(event -> {
            if(!fieldAdresseImage.getText().isEmpty()){
                imagesObservableList.add(fieldAdresseImage.getText());
                fieldAdresseImage.setText(null);
            }
            event.consume();
        });

        boutonPoster.setOnAction(event -> {
            List<Image> images = new ArrayList<>();
            for(String adresseImg : imagesObservableList){
                images.add(new Image(adresseImg));
            }

            List<Lien> liens = new ArrayList<>();
            for(String[] lien : liensObservableList){
                liens.add(new Lien(lien[1], lien[0]));
            }

            ControleurDonnees.posterMessage(ModeleDonnees.getUtilisateurConnecte(), fieldTitre.getText(),  messageArea.getText(), fieldMotsCles.getText().split(" "), images, liens);

            ModeleDonnees.getObsFenetrePrincipale().rafraichirMessages();

            Stage stage = (Stage) boutonAnnuler.getScene().getWindow();
            stage.close();

            event.consume();
        });

        boutonAnnuler.setOnAction(event -> {
            Stage stage = (Stage) boutonAnnuler.getScene().getWindow();
            stage.close();
            event.consume();
        });
    }

    /**
     * On donne à ObsFenetreModifierMessage le message qu'il faudra modifier dans le constructeur pour qu'il puisse pré-remplir ses champs à l'initialisation.
     * (Puisque le constructeur est appelé avant la méthode initialize).
     * @param message Le message dont il faut copier les données
     */
    public ObsFenetreModifierMessage(Message message){
        messageAffiche = message;
    }

    /**
     * On désactive le constructeur par défaut pour éviter des NullPointerException.
     */
    private ObsFenetreModifierMessage(){}
}
