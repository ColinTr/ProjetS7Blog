package controleur;

import modele.*;

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
        java.sql.Date dateDeCreation = new java.sql.Date(System.currentTimeMillis());

        Message message = new Message(titre, texteMessage, dateDeCreation);

        message.setUtilisateur(utilisateur);
        utilisateur.addMessage(message);

        for(String motCle : motsCles){
            //TODO REGARDER SI LES MOTS CLES EXISTENT DEJA, SINON LES CREER
        }

        for(Image image : images){
            message.addImage(image);
            image.setMessage(message);
            Connexion.getEntityManager().persist(image);
        }

        for(Lien lien : liens){
            message.addLien(lien);
            lien.setMessage(message);
            Connexion.getEntityManager().persist(lien);
        }

        Connexion.getEntityManager().persist(message);

        return true;
    }

}
