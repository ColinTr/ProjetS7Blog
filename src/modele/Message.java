package modele;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMessage;

    private String titre;

    @Column(columnDefinition="TEXT") //De base, Hibernate map un String comme un Varchar(255). Pour pouvoir stocker un plus grand String on ajoute cette annotation.
    private String texte;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lien> liens;

    @ManyToMany(mappedBy = "messages", fetch = FetchType.LAZY) //Si on supprime une image, on ne veut pas supprimer les mots clés
    private List<MotCle> motCles;

    @ManyToOne(fetch = FetchType.LAZY) //On ne met pas CascadeType.ALL car lorsqu'on supprime un message on ne veut pas supprimer son utilisateur
    private Utilisateur utilisateur;

    public Message(){
        images = new ArrayList<>();
        liens = new ArrayList<>();
        motCles = new ArrayList<>();
    }

    public Message(String titre, String texte, java.util.Date date){
        this.titre = titre;
        this.texte = texte;
        this.date = date;
        images = new ArrayList<>();
        liens = new ArrayList<>();
        motCles = new ArrayList<>();
    }

    //Accesseurs en lecture :
    public String getTitre() { return titre; }

    public String getTexte() { return texte; }

    public Date getDate() { return date; }

    public Utilisateur getUtilisateur() { return utilisateur; }

    //Accesseurs en écriture :
    public void setTitre(String titre) { this.titre = titre; }

    public void setTexte(String texte) { this.texte = texte; }

    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public void setImages(List<Image> images) { this.images = images; }

    public void setLiens(List<Lien> liens) { this.liens = liens; }

    public void setMotCles(List<MotCle> motCles) { this.motCles = motCles; }

    //Image
    public List<Image> getImages(){
        return images;
    }
    public void addImage(Image image){
        images.add(image);
    }
    public void removeImage(Image image){
        images.remove(image);
    }

    //Lien
    public List<Lien> getLiens(){
        return liens;
    }
    public void addLien(Lien lien){
        liens.add(lien);
    }
    public void removeLien(Lien lien){
        liens.remove(lien);
    }

    //Mot Cle
    public List<MotCle> getMotCles(){
        return motCles;
    }
    public void addMotCle(MotCle motCle){
        motCles.add(motCle);
    }
    public void removeMotCle(MotCle motCle){
        motCles.remove(motCle);
    }
}
