package modele;

import javax.persistence.*;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMessage;

    private String titre;
    private String texte;
    private List<String> images;
    private List<String> liens;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Utilisateur utilisateur;

    public Message(int idMessage, String titre, String texte, List<String> images, List<String> liens, Utilisateur utilisateur) {
        this.idMessage = idMessage;
        this.titre = titre;
        this.texte = texte;
        this.images = images;
        this.liens = liens;
        this.utilisateur = utilisateur;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getLiens() {
        return liens;
    }

    public void setLiens(List<String> liens) {
        this.liens = liens;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
