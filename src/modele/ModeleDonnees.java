package modele;

import vue.javafxobservers.ObsFenetrePrincipale;

import java.util.ArrayList;
import java.util.List;

public class ModeleDonnees {
    private final Utilisateur utilisateurConnecte;
    private final List<Message> messageList = new ArrayList<>();
    private final List<MotCle> motCleList = new ArrayList<>();
    private final ObsFenetrePrincipale obsFenetrePrincipale; //On l'initialise à null, mais il sera rédfini une fois qu'une instance de ObsFenetrePrincipale est créée

    public ModeleDonnees(Utilisateur utilisateurConnecte, ObsFenetrePrincipale obsFenetrePrincipale) {
        this.utilisateurConnecte = utilisateurConnecte;
        this.obsFenetrePrincipale = obsFenetrePrincipale;
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
}
