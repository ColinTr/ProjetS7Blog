package modele;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUtilisateur;

    private String nom;
    private String prenom;
    private String adresse;
    private String mail;
    private String motDePasse;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    public Utilisateur() {
        messages = new ArrayList<Message>();
    }

    public Utilisateur(String nom, String prenom, String adresse, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.mail = mail;
        this.motDePasse = motDePasse;
        messages = new ArrayList<Message>();
    }



    public Message getMessage(int i){
        return messages.get(i);
    }

    public void addMessage(Message m){
        messages.add(m);
    }

    public void removeMessage(Message m){
        messages.remove(m);
    }

    public List<Message> getMessages(){
        return messages;
    }

}
