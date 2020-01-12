package controleur;

import modele.Image;

public class ImageDAO {
    public static void ajouterImage(Image image){
        if (!Connexion.getEntityManager().contains(image)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().persist(image);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }
}
