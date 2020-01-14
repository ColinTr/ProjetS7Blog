package controleur;

import modele.Image;
import modele.Lien;
import modele.Message;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import modele.MotCle;


public class MessageDAO {

    public static List<Message> getAllMessages(){
        List<Message> listeARetourner = new ArrayList<>();

        Query query = Connexion.getEntityManager().createQuery("SELECT m FROM Message m");
        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Message) o) );
        }

        return listeARetourner;
    }

    public static void ajouterMessage(Message message){
        if (!Connexion.getEntityManager().contains(message)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().persist(message);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }

    public static void supprimerMessage(Message message){
        if (Connexion.getEntityManager().contains(message)){
            Connexion.beginTransaction();
            try{
                Connexion.getEntityManager().remove(message);
                Connexion.commitTransaction();
            } catch (Exception e){
                e.printStackTrace();
                Connexion.rollbackTransaction();
            }
        }
    }

    public static boolean modifierMessage(Message message, String titre, String texteMessage, String[] motsCles, List<Image> images, List<Lien> liens){
        //TODO
        return true;
    }

    public static List<Message> recupererMessagesAUneDateDonnee(java.util.Date date){
        //TODO
        return null;
    }

    public static List<Message> recupererMessagesAvecMotsCles(List<MotCle> motCles){
        //TODO
        return null;
    }

}
