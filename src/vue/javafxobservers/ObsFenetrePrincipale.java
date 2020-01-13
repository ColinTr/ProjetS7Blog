package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controleur.MessageDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.Message;
import modele.MotCle;
import modele.Utilisateur;
import vue.FenetreDeConnexion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ObsFenetrePrincipale implements Initializable {

    private static Utilisateur utilisateurConnecte;
    @FXML private JFXButton boutonPosterUnMessage;
    @FXML private DatePicker datePicker;
    @FXML private JFXTextField fieldMotsCles;
    @FXML private JFXButton boutonChercher;
    @FXML private JFXButton boutonSupprimerFiltres;
    @FXML private VBox vboxPrincipale;
    @FXML private ScrollPane scrollpanePrincipale;
    @FXML private Text textNomUtilisateur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textNomUtilisateur.setText(utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom());

        boutonSupprimerFiltres.setOnAction(event -> {
            datePicker.setValue(null);
            fieldMotsCles.setText(null);
            rafraichirMessages();

            event.consume();
        });

        boutonChercher.setOnAction(event -> {
            rafraichirMessages();

            event.consume();
        });

        boutonPosterUnMessage.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fenetreCreerMessage.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                ObsFenetreCreerMessage obsCreerMessage = loader.getController();
                obsCreerMessage.setUtilisateurConnecte(utilisateurConnecte);
                obsCreerMessage.setObsFenetrePrincipale(this);
                stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                stage.setTitle("Poster un nouveau message");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            event.consume();
        });

        rafraichirMessages();
    }

    public void rafraichirMessages(){

        LocalDate localDate = datePicker.getValue();

        String[] motCles = null;
        if(fieldMotsCles.getText() != null && !fieldMotsCles.getText().isEmpty()){
            motCles = fieldMotsCles.getText().split(" ");
        }

        vboxPrincipale.getChildren().clear();

        List<Message> messages = MessageDAO.getAllMessages();

        //Trie les messages dans l'ordre croissant des dates :
        messages.sort(Comparator.comparing(Message::getDate));
        //Donc on inverse la liste pour avoir les messages les plus récents d'abord :
        Collections.reverse(messages);

        for(Message m : messages){
            if(appliquerFiltres(localDate, motCles, m)){
                Parent root = null;
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/message.fxml"));
                    root = loader.load();
                    ObsMessage obsMessage = loader.getController();

                    obsMessage.setObsFenetrePrincipale(this);
                    obsMessage.setMessageAffiche(m);

                    List<javafx.scene.image.Image> listeImages = new ArrayList<>();
                    for(modele.Image img : m.getImages()){
                        listeImages.add(new javafx.scene.image.Image(img.getAdresseImage()));
                    }

                    obsMessage.definirMessage(m.getUtilisateur().getPrenom() + " " + m.getUtilisateur().getNom(), m.getDate().toString(), m.getTitre(), m.getTexte(), listeImages, m.getLiens(), m.getMotCles());

                    if(!m.getUtilisateur().getAdresseMail().equals(utilisateurConnecte.getAdresseMail())) { obsMessage.supprimerCommandes(); }

                    if(m.getImages().isEmpty()){ obsMessage.supprimerImages(); }

                    if(m.getLiens().isEmpty()){ obsMessage.supprimerLiens(); }

                    if(m.getMotCles().isEmpty()){ obsMessage.supprimerMotsCles(); }

                } catch(IOException e){
                    e.printStackTrace();
                }
                vboxPrincipale.getChildren().add(root);
            }
        }
    }

    private boolean appliquerFiltres(LocalDate date, String[] motsCles, Message message){

        LocalDate dateMessage = message.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if(date != null){
            if(motsCles != null && motsCles.length > 0){
                return dateMessage.equals(date) && messageContientMotsCles(motsCles, message);
            }
            else return dateMessage.equals(date);
        }
        else{
            if(motsCles != null && motsCles.length > 0) {
                return messageContientMotsCles(motsCles, message);
            }
            else{
                return true;
            }
        }
    }

    private boolean messageContientMotsCles(String[] motsCles, Message message){
        boolean bool = true;

        List<String> motsClesMessage = new ArrayList<>();
        for(MotCle m : message.getMotCles()){
            motsClesMessage.add(m.getMotCle());
        }

        for(String motCle : motsCles){
            if (!motsClesMessage.contains(motCle)) {
                bool = false;
                break;
            }
        }

        return bool;
    }

    public static void setUtilisateurConnecte(Utilisateur utilisateur){
        utilisateurConnecte = utilisateur;
    }

}