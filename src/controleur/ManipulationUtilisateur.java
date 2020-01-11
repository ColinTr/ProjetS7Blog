package controleur;

import modele.Utilisateur;

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

    public static void posterMessage(){}

}
