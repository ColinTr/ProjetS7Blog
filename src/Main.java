import controleur.Connexion;
import modele.MotCle;

public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        MotCle motCle = new MotCle("test");

        Connexion.getEntityManager().persist(motCle);

        Connexion.commitTransaction();

        Connexion.close();
    }
}