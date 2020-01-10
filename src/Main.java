import controleur.Connexion;
import modele.Message;

public class Main {
    public static void main( String[] args ) {
        Connexion.init("modeCreate");

        Connexion.beginTransaction();

        Message message = new Message();

        Connexion.getEntityManager().persist(message);

        Connexion.commitTransaction();

        Connexion.close();
    }
}