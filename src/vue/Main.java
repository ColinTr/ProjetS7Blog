package vue;

import controleur.Connexion;
import controleur.ManipulationUtilisateur;
import controleur.UtilisateurDAO;
import modele.Message;
import modele.Utilisateur;

/**
 * Cette classe permet de créer une base de données de test pré-remplie
 */
public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        Utilisateur azer = new Utilisateur("NomAZER", "PrenomAZER", "10 rue olivier debré", "azer", UtilisateurDAO.SHA512("azer"));
        Utilisateur colin = new Utilisateur("Troisemaine", "Colin", "10 rue des peupliers", "colin.troisemaine@etu.univ-tours.fr", UtilisateurDAO.SHA512("password"));
        Utilisateur guillaume = new Utilisateur("Bouchard", "Guillaume", "64 Avenue Jean Portalis", "guillaume.bouchard@etu.univ-tours.fr", UtilisateurDAO.SHA512("password"));

        String shortLoremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt u";
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";

        Message m1 = new Message("titre message 1", shortLoremIpsum, new java.sql.Date(System.currentTimeMillis()));
        Message m2 = new Message("titre message 2", loremIpsum, new java.sql.Date(System.currentTimeMillis()));
        Message m3 = new Message("titre message 3", shortLoremIpsum, new java.sql.Date(System.currentTimeMillis()));
        Message m4 = new Message("titre message 4", loremIpsum, new java.sql.Date(System.currentTimeMillis()));
        Message m5 = new Message("titre message 5", loremIpsum, new java.sql.Date(System.currentTimeMillis()));

        modele.Image img1 = new modele.Image("https://humancoders-formations.s3.amazonaws.com/uploads/course/logo/93/formation-git-avance.png");
        modele.Image img2 = new modele.Image("https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Octicons-mark-github.svg/1200px-Octicons-mark-github.svg.png");
        modele.Image img3 = new modele.Image("https://upload.wikimedia.org/wikipedia/fr/f/fe/SceneBuilderLogo.png");

        m1.addImage(img1);
        m1.addImage(img2);
        m1.addImage(img3);

        m1.setUtilisateur(azer);
        m2.setUtilisateur(colin);
        m3.setUtilisateur(guillaume);
        m4.setUtilisateur(azer);
        m5.setUtilisateur(colin);

        Connexion.getEntityManager().persist(m1);
        Connexion.getEntityManager().persist(m2);
        Connexion.getEntityManager().persist(m3);
        Connexion.getEntityManager().persist(m4);
        Connexion.getEntityManager().persist(m5);

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