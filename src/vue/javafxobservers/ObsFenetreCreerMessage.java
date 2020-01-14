package vue.javafxobservers;

import com.jfoenix.controls.*;
import controleur.ControleurDonnees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modele.Image;
import modele.Lien;
import modele.ModeleDonnees;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ObsFenetreCreerMessage  implements Initializable {
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
    @FXML private TextField fieldTexteLien;
    @FXML private TextField fieldAdresseLien;
    @FXML private JFXListView<String[]> liensListView;
    private static ObservableList<String[]> liensObservableList = FXCollections.observableArrayList();
    @FXML private JFXButton boutonPoster;
    @FXML private JFXButton boutonAnnuler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagesListView.setCellFactory(param -> new XCellString());
        imagesListView.setItems(imagesObservableList);

        liensListView.setCellFactory(param -> new XCellStringTable());
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
     * Sous classe permettant de créer des cellules avec l'adresse de l'image et le bouton supprimer.
     */
    static class XCellString extends JFXListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Suppr");

        public XCellString() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setId("boutonSupprimerDeListe");
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }

    /**
     * Sous classe permettant de créer des cellules avec le texte du lien, son adresse et le bouton supprimer.
     * String[0] = Texte du lien
     * String[1] = Adresse du lien
     */
    static class XCellStringTable extends JFXListCell<String[]> {
        HBox hbox = new HBox();
        Label labelTexte = new Label("");
        Label labelDelimiter = new Label(" | ");
        Label labelAdresse = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Suppr");

        public XCellStringTable() {
            super();

            hbox.getChildren().addAll(labelTexte, labelDelimiter, labelAdresse, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setId("boutonSupprimerDeListe");
        }

        @Override
        protected void updateItem(String[] item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                labelTexte.setText(item[0]);
                labelAdresse.setText(item[1]);
                setGraphic(hbox);
            }
        }
    }
}