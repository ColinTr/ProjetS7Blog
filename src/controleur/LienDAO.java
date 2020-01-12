package controleur;

import modele.Lien;

public class LienDAO {
    public static void ajouterLien(Lien lien){
        if (!Connexion.getEntityManager().contains(lien)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().persist(lien);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }
}
