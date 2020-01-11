package vue.javafxobservers;

import controleur.MessageDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import modele.Message;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ObsFenetrePrincipale implements Initializable {

    @FXML VBox vboxPrincipale;
    @FXML ScrollPane scrollpanePrincipale;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirMessages();
    }

    private void rafraichirMessages(){
        List<Message> messages = MessageDAO.getAllMessages();
        for(Message m : messages){
            Parent root = null;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/message.fxml"));
                root = loader.load();
                ObsMessage obsMessage = loader.getController();
                obsMessage.definirMessage(m.getUtilisateur().getPrenom() + " " + m.getUtilisateur().getNom(), m.getDate().toString(), m.getTitre(), m.getTexte());
            } catch(IOException e){
                e.printStackTrace();
            }
            vboxPrincipale.getChildren().add(root);
        }
    }
}