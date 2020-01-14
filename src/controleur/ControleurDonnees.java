package controleur;

import modele.*;
import org.dom4j.rule.Mode;
import vue.javafxobservers.ObsFenetrePrincipale;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ControleurDonnees {

    public static boolean inscription(String nom, String prenom, String adresse, String mail, String motDePasse){
        if (UtilisateurDAO.getUtilisateur(mail) == null){
            Utilisateur utilisateur = new Utilisateur(nom,prenom,adresse,mail,UtilisateurDAO.SHA512(motDePasse));
            UtilisateurDAO.ajouterUtilisateur(utilisateur);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Méthode que la vue appelle lorsque l'utilisateur appuie sur le bouton "supprimer" d'un message.
     * Cette méthode supprime donc le message de la BDD ET du modèle de données de l'application.
     * @param message Le message à supprimer.
     */
    public static void supprimerMessage(Message message){
        MessageDAO.supprimerMessage(message);
        ModeleDonnees.getMessageList().remove(message);
    }

    /**
     * Cette méthode permet de modifier un message déjà posté.
     * @param message Le message à modifier.
     * @param titre Le nouveau titre.
     * @param texteMessage Le nouveau texte.
     * @param motsCles Les nouveaux mots-clés.
     * @param images Les nouvelles images.
     * @param liens les nouveaux liens.
     */
    public static void modifierMessage(Message message, String titre, String texteMessage, String[] motsCles, List<Image> images, List<Lien> liens){
        MessageDAO.modifierMessage(message, titre, texteMessage, motsCles, images, liens);
        int indexMessage = ModeleDonnees.getMessageList().indexOf(message);
        if(indexMessage != -1){
            ModeleDonnees.getMessageList().get(indexMessage).setTitre(titre);
            ModeleDonnees.getMessageList().get(indexMessage).setTexte(texteMessage);
            ModeleDonnees.getMessageList().get(indexMessage).setTitre(titre);
            ModeleDonnees.getMessageList().get(indexMessage).setTitre(titre);
            ModeleDonnees.getMessageList().get(indexMessage).setTitre(titre);
        }
    }

    /**
     * Cette méthode permet de poster un message à la date actuelle.
     * Elle ajoute le message à la BDD ET au modèle de données de l'application.
     * @param utilisateur L'utilisateur qui poste le message
     * @param titre Le titre du message
     * @param texteMessage Le texte du message
     * @param motsCles Les mots clés associés au message
     * @param images Les images associées au message
     * @param liens Les liens associés au message
     */
    public static void posterMessage(Utilisateur utilisateur, String titre, String texteMessage, String[] motsCles, List<Image> images, List<Lien> liens){
        Date dateDeCreation = new Date(System.currentTimeMillis());

        Message message = new Message(titre, texteMessage, dateDeCreation);

        message.setUtilisateur(utilisateur);
        utilisateur.addMessage(message);

        for(String motCle : motsCles){
            MotCle mc = new MotCle(motCle);
            message.addMotCle(mc);
            mc.ajouterMessage(message);
        }

        for(Image image : images){
            message.addImage(image);
            image.setMessage(message);
        }

        for(Lien lien : liens){
            message.addLien(lien);
            lien.setMessage(message);
        }

        MessageDAO.ajouterMessage(message);

        ModeleDonnees.getMessageList().add(message);
    }

    /**
     * Cette méthode permet au lancement de charger la liste de toutes les données affichées à partir de la BDD.
     * @param obsFenetrePrincipale La fenetre principale.
     * @param utilisateurConnecte L'utilisateur connecté.
     */
    public static void initialiserDonnees(ObsFenetrePrincipale obsFenetrePrincipale, Utilisateur utilisateurConnecte){
        ModeleDonnees.setObsFenetrePrincipale(obsFenetrePrincipale);
        ModeleDonnees.setUtilisateurConnecte(utilisateurConnecte);
        ModeleDonnees.setMessageList(MessageDAO.getAllMessages());
        ModeleDonnees.setMotCleList(MotCleDAO.getAllMotsCles());
    }

    /**
     * Cette fonction permet de renvoyer une sous liste des messages du ModeleDonnes correspondants aux mots-clés et à la date rentrés.
     * @param motsCles l'ensemble des mots-clés.
     * @param date la date des messages à afficher.
     * @return La liste des messages filtrés et triée par plus grande date d'abord.
     */
    public static List<Message> filtrerMessage(String[] motsCles, LocalDate date){

        List<Message> messages = new ArrayList<>();

        for(Message message : ModeleDonnees.getMessageList()){
            LocalDate dateMessage = message.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(date != null){
                if(motsCles != null && motsCles.length > 0){
                    if(dateMessage.equals(date) && messageContientMotsCles(motsCles, message)){
                        messages.add(message);
                    }
                }
                else if(dateMessage.equals(date)){
                    messages.add(message);
                }
            }
            else{
                if(motsCles != null && motsCles.length > 0) {
                    if(messageContientMotsCles(motsCles, message)){
                        messages.add(message);
                    }
                }
                else{
                    messages.add(message);
                }
            }
        }

        //Trie les messages dans l'ordre croissant des dates :
        messages.sort(Comparator.comparing(Message::getDate));
        //Donc on inverse la liste pour avoir les messages les plus récents d'abord :
        Collections.reverse(messages);

        return messages;
    }

    private static boolean messageContientMotsCles(String[] motsCles, Message message){
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

    /**
     * Constructeur privé pour interdire la création d'une instance de ManipulationUtilisateur.
     */
    private ControleurDonnees(){
    }
}
