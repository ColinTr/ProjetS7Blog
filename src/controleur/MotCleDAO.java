package controleur;

import modele.MotCle;

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
}
