import controleur.Connexion;
import controleur.UtilisateurDAO;
import modele.Utilisateur;

public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        Utilisateur azer = new Utilisateur("Nom", "Prenom", "10 rue des peupliers", "monAdresseMail@mail.com", UtilisateurDAO.SHA512("azer"));

        Connexion.getEntityManager().persist(azer);

        Connexion.commitTransaction();

        Connexion.close();
    }
}