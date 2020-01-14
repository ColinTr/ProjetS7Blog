package controleur;

import modele.Image;
import modele.Lien;
import modele.Message;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modele.MotCle;


public class MessageDAO {

    public static List<Message> getAllMessages(){
        List<Message> listeARetourner = new ArrayList<>();

        String queryString = "SELECT m FROM Message m";

        Query query = Connexion.getEntityManager().createQuery(queryString);

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

    public static boolean modifierMessage(Message message, String titre, String texteMessage, List<MotCle> motsCles, List<Image> images, List<Lien> liens){
        Connexion.beginTransaction();
        try{
            message.setTitre(titre);
            message.setTexte(texteMessage);
            message.setMotCles(motsCles);
            message.setImages(images);
            message.setLiens(liens);
            Connexion.commitTransaction();
            return true;
        } catch (Exception e){
            Connexion.rollbackTransaction();
            return false;
        }
    }

    /**
     * Cette méthode renvoie la liste de tous les messages qui possèdent au moins TOUS les mots-clés de la liste passée en paramètre.
     * @param motCles La liste des mots-clés.
     * @return La liste des messages qui correspondent.
     */
    public static List<Message> selectionnerMessages(List<String> motCles){
        List<Message> listeARetourner = new ArrayList<>();

        String queryString = "SELECT b FROM MotCle a JOIN a.messages b WHERE a.motCle IN :motCles GROUP BY b.idMessage HAVING COUNT(a) = :longueur";

        Query query = Connexion.getEntityManager().createQuery(queryString);

        query.setParameter("motCles", motCles);
        Long size = Integer.toUnsignedLong(motCles.size());
        query.setParameter("longueur", size);

        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Message) o) );
        }

        return listeARetourner;
    }

    /**
     * Cette méthode sélectionne uniquement les messages ayant été postés à une date donnée (année, mois et jour).
     * @param date La date.
     * @return La liste des messages qui correspondent.
     */
    public static List<Message> selectionnerMessages(LocalDate date){
        List<Message> listeARetourner = new ArrayList<>();

        String queryString = "SELECT m FROM Message m WHERE day(m.date) = :day AND month(m.date) = :month AND year(m.date) = :year";

        Query query = Connexion.getEntityManager().createQuery(queryString);

        query.setParameter("day", date.getDayOfMonth());
        query.setParameter("month", date.getMonthValue());
        query.setParameter("year", date.getYear());

        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Message) o) );
        }

        return listeARetourner;
    }

    /**
     * Cette méthode est la fusion des méthodes qui sélectionne par mots-clés et par date.
     * Donc elle renvoie les messages ayant été postés à une date donnée et possédant au moins tous les mots-clés de la liste.
     * @param motCles La liste des mots-clés.
     * @param date La date.
     * @return La liste des messages qui correspondent.
     */
    public static List<Message> selectionnerMessages(List<String> motCles, LocalDate date){
        List<Message> listeARetourner = new ArrayList<>();

        String queryString = "SELECT m FROM Message m WHERE m.idMessage IN (SELECT b.idMessage FROM MotCle a JOIN a.messages b WHERE a.motCle IN :motCles GROUP BY b.idMessage HAVING COUNT(a) = :longueur) AND day(m.date) = :day AND month(m.date) = :month AND year(m.date) = :year";

        Query query = Connexion.getEntityManager().createQuery(queryString);

        query.setParameter("motCles", motCles);
        Long size = Integer.toUnsignedLong(motCles.size());
        query.setParameter("longueur", size);

        query.setParameter("day", date.getDayOfMonth());
        query.setParameter("month", date.getMonthValue());
        query.setParameter("year", date.getYear());

        List results = query.getResultList();

        for(Object o : results){
            listeARetourner.add( ((Message) o) );
        }

        return listeARetourner;
    }
}