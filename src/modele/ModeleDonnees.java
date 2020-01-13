package modele;

import vue.javafxobservers.ObsFenetrePrincipale;

import java.util.ArrayList;
import java.util.List;

public class ModeleDonnees {
    private final Utilisateur utilisateurConnecte = null;
    private final List<Message> messageList = new ArrayList<>();
    private final List<MotCle> motCleList = new ArrayList<>();
    private final ObsFenetrePrincipale obsFenetrePrincipale = null; //On l'initialise à null, mais il sera rédfini une fois qu'une instance de ObsFenetrePrincipale est créée

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte){
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public List<MotCle> getMotCleList() {
        return motCleList;
    }

    public ObsFenetrePrincipale getObsFenetrePrincipale() {
        return obsFenetrePrincipale;
    }

    public void setObsFenetrePrincipale(ObsFenetrePrincipale obsFenetrePrincipale){
        this.obsFenetrePrincipale = obsFenetrePrincipale;
    }
}
