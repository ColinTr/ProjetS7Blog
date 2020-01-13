package controleur;

import modele.Message;
import modele.MotCle;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MotCleDAO {
    public static void ajouterMotCle(MotCle motCle){
        if (!Connexion.getEntityManager().contains(motCle)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().persist(motCle);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }

    public static List<MotCle> getAllMotsCles() {
        List<MotCle> listeARetourner = new ArrayList<>();

        Query query = Connexion.getEntityManager().createQuery("SELECT m FROM MotCle m");
        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((MotCle) o) );
        }

        return listeARetourner;
    }
}
