package controleur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public abstract class Connexion {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static boolean init(String mode){
        try {
            emf = Persistence.createEntityManagerFactory(mode);
            em = emf.createEntityManager();
            return true;
        } catch(PersistenceException a){
            return false;
        }
    }

    public static boolean init(String mode, String url, String user, String password){
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", url);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        try{
            emf = Persistence.createEntityManagerFactory(mode,properties);
            em = emf.createEntityManager();
            return true;
        }
        catch(PersistenceException e){
            return false;
        }
    }

    public static void close(){
        em.close();
        emf.close();
    }

    public static EntityManager getEntityManager() { return em; }

    public static void beginTransaction(){
        em.getTransaction().begin();
    }

    public static void commitTransaction(){
        em.getTransaction().commit();
    }

    public static void rollbackTransaction(){
        em.getTransaction().rollback();
    }

}
