package controleur;

import modele.Utilisateur;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public static List<Utilisateur> getAllUtilisateur(){
        List<Utilisateur> listeARetourner = new ArrayList<>();

        Query query = Connexion.getEntityManager().createQuery("SELECT u FROM Utilisateur u");
        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Utilisateur) o) );
        }

        return listeARetourner;
    }

    public static void ajouterUtilisateur(Utilisateur utilisateur){
        if (!Connexion.getEntityManager().contains(utilisateur)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().persist(utilisateur);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }

    public static void supprimerUtilisateur(Utilisateur utilisateur){
        if (Connexion.getEntityManager().contains(utilisateur)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().remove(utilisateur);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }

}
