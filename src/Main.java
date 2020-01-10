import com.sun.xml.bind.Util;
import controleur.Connexion;
import controleur.MessageDAO;
import controleur.UtilisateurDAO;
import modele.Message;
import modele.MotCle;
import modele.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        MotCle motCle = new MotCle("test");

        MotCle motCle1 = new MotCle("test");

        Connexion.getEntityManager().persist(motCle);

        Connexion.commitTransaction();

        Connexion.close();
    }
}