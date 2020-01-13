package modele;

import vue.javafxobservers.ObsFenetrePrincipale;

import java.util.List;

public final class ModeleDonnees {
    private static Utilisateur utilisateurConnecte;
    private static List<Message> messageList;
    private static List<MotCle> motCleList;
    private static ObsFenetrePrincipale obsFenetrePrincipale;

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static List<Message> getMessageList() {
        return messageList;
    }

    public static List<MotCle> getMotCleList() {
        return motCleList;
    }

    public static ObsFenetrePrincipale getObsFenetrePrincipale() {
        return obsFenetrePrincipale;
    }

    public static void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        ModeleDonnees.utilisateurConnecte = utilisateurConnecte;
    }

    public static void setMessageList(List<Message> messageList) {
        ModeleDonnees.messageList = messageList;
    }

    public static void setMotCleList(List<MotCle> motCleList) {
        ModeleDonnees.motCleList = motCleList;
    }

    public static void setObsFenetrePrincipale(ObsFenetrePrincipale obsFenetrePrincipale) {
        ModeleDonnees.obsFenetrePrincipale = obsFenetrePrincipale;
    }

    /**
     * Constructeur privé pour interdire la création d'une instance de ModeleDonnees.
     */
    private ModeleDonnees(){
    }
}