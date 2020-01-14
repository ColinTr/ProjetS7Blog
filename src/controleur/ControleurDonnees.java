package controleur;

import modele.*;
import vue.javafxobservers.ObsFenetrePrincipale;

import java.time.LocalDate;
import java.util.*;

public class ControleurDonnees {

    public static boolean inscription(String nom, String prenom, String adresse, String mail, String motDePasse){
        //On vérifie qu'un utilisateur avec la même adresse Email n'existe pas déjà :
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
        System.out.println(ModeleDonnees.getMessageList().indexOf(message));
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
    public static void modifierMessage(Message message, String titre, String texteMessage, List<String> motsCles, List<String> images, List<String[]> liens){
        //On construit les listes d'objets mots-clés, images et liens ici :
        List<MotCle> listMotsCles = new ArrayList<>();
        List<Image> listImages = new ArrayList<>();
        List<Lien> listLiens = new ArrayList<>();

        for(String motCleString : motsCles){

            MotCle motCle = MotCleDAO.getMotCle(motCleString);
            if (motCle==null){
                motCle = new MotCle(motCleString);
                message.addMotCle(motCle);
                motCle.ajouterMessage(message);
                MotCleDAO.ajouterMotCle(motCle);
                ModeleDonnees.getMotCleList().add(motCle);
            }
            else{
                message.addMotCle(motCle);
                motCle.ajouterMessage(message);
            }
        }

        for(String imageString : images){
            listImages.add(new Image(imageString));
        }

        for(String[] lienString : liens){
            listLiens.add(new Lien(lienString[0], lienString[1]));
        }

        MessageDAO.modifierMessage(message, titre, texteMessage, listMotsCles, listImages, listLiens);

        ModeleDonnees.getObsFenetrePrincipale().rafraichirMessages();
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
    public static void posterMessage(Utilisateur utilisateur, String titre, String texteMessage, List<String> motsCles, List<String> images, List<String[]> liens){
        Date dateDeCreation = new Date(System.currentTimeMillis());

        Message message = new Message(titre, texteMessage, dateDeCreation);

        message.setUtilisateur(utilisateur);
        utilisateur.addMessage(message);

        for(String motCleString : motsCles){

            MotCle motCle = MotCleDAO.getMotCle(motCleString);
            if (motCle==null){
                motCle = new MotCle(motCleString);
                message.addMotCle(motCle);
                motCle.ajouterMessage(message);
                MotCleDAO.ajouterMotCle(motCle);
                ModeleDonnees.getMotCleList().add(motCle);
            }
            else{
                message.addMotCle(motCle);
                motCle.ajouterMessage(message);
            }
        }

        for(String imageString : images){
            Image image = new Image(imageString);
            message.addImage(image);
            image.setMessage(message);
        }

        for(String[] lienString : liens){
            Lien lien = new Lien(lienString[0], lienString[1]);
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
     * Cette méthode renvoie la liste des messages correspondant aux critères rentrés.
     * Si motsCles ou date est null, ils seront ignorés.
     * @param motsCles La liste des mots-clés.
     * @param date La date.
     * @return La liste des messages qui correspondent.
     */
    public static List<Message> getMessagesFiltres(String[] motsCles, LocalDate date){

        List<Message> listeMessages;

        if(motsCles == null && date == null){
            listeMessages = MessageDAO.getAllMessages();
        } else if(date == null){
            listeMessages = MessageDAO.selectionnerMessages(Arrays.asList(motsCles));
        } else if(motsCles == null){
            listeMessages = MessageDAO.selectionnerMessages(date);
        } else{
            listeMessages = MessageDAO.selectionnerMessages(Arrays.asList(motsCles), date);
        }

        //On inverse pour afficher les messages les plus récents d'abord :
        Collections.reverse(listeMessages);

        return listeMessages;
    }

    /**
     * Constructeur privé pour interdire la création d'une instance de ManipulationUtilisateur.
     */
    private ControleurDonnees(){
    }
}