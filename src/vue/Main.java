package vue;

import controleur.Connexion;
import controleur.ManipulationUtilisateur;
import controleur.UtilisateurDAO;
import modele.Utilisateur;

/**
 * Cette classe permet de créer une base de données de test pré-remplie
 */
public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        Utilisateur azer = new Utilisateur("Nom", "Prenom", "10 rue olivier debré", "azer", UtilisateurDAO.SHA512("azer"));
        Utilisateur colin = new Utilisateur("Troisemaine", "Colin", "10 rue des peupliers", "colin.troisemaine@etu.univ-tours.fr", UtilisateurDAO.SHA512("password"));
        Utilisateur guillaume = new Utilisateur("Bouchard", "Guillaume", "64 Avenue Jean Portalis", "guillaume.bouchard@etu.univ-tours.fr", UtilisateurDAO.SHA512("password"));

        Connexion.getEntityManager().persist(azer);
        Connexion.getEntityManager().persist(colin);
        Connexion.getEntityManager().persist(guillaume);

        Connexion.commitTransaction();


        if (ManipulationUtilisateur.inscription("","","","aze","1234")){
            System.out.println("Test1 ok");
        }

        if (!ManipulationUtilisateur.inscription("","","","azer","1234")){
            System.out.println("Test2 ok");
        }

        Connexion.close();
    }
}