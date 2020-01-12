package modele;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur {

    @Id
    private String adresseMail;

    private String nom;
    private String prenom;
    private String adresse;
    private String motDePasse;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //CascadeType.ALL car on veut supprimer tous les messages d'un utilisateur lorsqu'on le supprime
    private List<Message> messages;

    public Utilisateur() {
        messages = new ArrayList<>();
    }

    public Utilisateur(String nom, String prenom, String adresse, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.adresseMail = mail;
        this.motDePasse = motDePasse;
        messages = new ArrayList<>();
    }

    public void addMessage(Message m){
        messages.add(m);
    }

    public void removeMessage(Message m){
        messages.remove(m);
    }


    //========================== Getters ==========================

    public List<Message> getMessages(){
        return messages;
    }

    public Message getMessage(int i){
        return messages.get(i);
    }

    public String getNom() { return nom; }

    public String getPrenom() { return prenom; }

    public String getAdresse() { return adresse; }

    public String getAdresseMail() { return adresseMail; }

    public String getMotDePasse() { return motDePasse; }
}