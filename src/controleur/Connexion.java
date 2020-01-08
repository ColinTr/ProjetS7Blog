package controleur;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public abstract class Connexion {

    private static EntityManager em;

    public static boolean init(String mode){
        try {
            em = Persistence.createEntityManagerFactory(mode).createEntityManager();
            return true;
        } catch(PersistenceException a){
            return false;
        }
    }

    public static boolean begin(){
        return true;
    }

    public static boolean commit(){
        return true;
    }

    public static void init(String mode, String url, String user, String password){
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", url);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        //em = Persistence.createEntityManagerFactory(mode,properties);
    }

    public static void close(){
        em.close();
    }

    public static EntityManager getEntityManager() { return em; }

}
