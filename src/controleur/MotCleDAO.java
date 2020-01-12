package controleur;

import modele.MotCle;

public class MotCleDAO {
    public boolean estCeQueMotCleExiste(MotCle motCle){
        MotCle motCleBDD = Connexion.getEntityManager().find(motCle.getClass(), motCle.getMotCle());

        return (motCleBDD != null);
    }
}
