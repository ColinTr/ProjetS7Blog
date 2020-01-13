package controleur;

import modele.*;

import java.util.Date;
import java.util.List;

public class ManipulationUtilisateur {

    public static boolean inscription(String nom, String prenom, String adresse, String mail, String motDePasse){
        if (UtilisateurDAO.getUtilisateur(mail) == null){
            Utilisateur utilisateur = new Utilisateur(nom,prenom,adresse,mail,UtilisateurDAO.SHA512(motDePasse));
            UtilisateurDAO.ajouterUtilisateur(utilisateur);
            //Ajouter à la table des Utilisateurs en local
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Cette méthode permet de poster un message à la date actuelle
     * @param utilisateur L'utilisateur qui poste le message
     * @param titre Le titre du message
     * @param texteMessage Le texte du message
     * @param motsCles Les mots clés associés au message
     * @param images Les images associées au message
     * @param liens Les liens associés au message
     */
    public static boolean posterMessage(Utilisateur utilisateur, String titre, String texteMessage, String[] motsCles, List<Image> images, List<Lien> liens){
        Date dateDeCreation = new Date(System.currentTimeMillis());

        Message message = new Message(titre, texteMessage, dateDeCreation);

        message.setUtilisateur(utilisateur);
        utilisateur.addMessage(message);

        for(String motCle : motsCles){
            MotCle mc = new MotCle(motCle);
            message.addMotCle(mc);
            mc.ajouterMessage(message);
            //MotCleDAO.ajouterMotCle(mc); //Ne l'ajoutera pas si il existe déjà dans la BDD
        }

        for(Image image : images){
            message.addImage(image);
            image.setMessage(message);
            //ImageDAO.ajouterImage(image);
        }

        for(Lien lien : liens){
            message.addLien(lien);
            lien.setMessage(message);
            //LienDAO.ajouterLien(lien);
        }

        MessageDAO.ajouterMessage(message);

        return true;
    }

}
