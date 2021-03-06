package controleur;

import modele.Utilisateur;

import javax.persistence.Query;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public static List<Utilisateur> getAllUtilisateur(){
        List<Utilisateur> listeARetourner = new ArrayList<>();

        String queryString = "SELECT u FROM Utilisateur u";

        Query query = Connexion.getEntityManager().createQuery(queryString);

        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Utilisateur) o) );
        }

        return listeARetourner;
    }

    public static Utilisateur getUtilisateur(String mail){
        return Connexion.getEntityManager().find(Utilisateur.class, mail);
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

    /**
     * Fonction permettant de tester les paramètres de connexion d'un utilisateur.
     * @param adresseMail : L'adresse mail.
     * @param mdPasse : Le mot de passe en clair.
     * @return : L'utilisateur correspondant (null si aucun).
     */
    public static Utilisateur testerAuthentification(String adresseMail, String mdPasse){
        Utilisateur utilisateur = null;

        String queryString = "SELECT u FROM Utilisateur u WHERE u.motDePasse = :mdp AND u.adresseMail = :mail ";

        Query query = Connexion.getEntityManager().createQuery(queryString);

        query.setParameter("mdp", SHA512(mdPasse));
        query.setParameter("mail", adresseMail);

        List results = query.getResultList();

        if(!results.isEmpty()){
            utilisateur = (Utilisateur) results.get(0);
        }

        return utilisateur;
    }

    /**
     * Fonction permettant de hacher un String selon la fonction Sha512.
     * @param str le String à hacher.
     * @return le String haché.
     */
    public static String SHA512(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(str.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}