package modele;

import javax.persistence.*;

@Entity
public class Lien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLien;

    private String adresseLien;
    private String texteLien;

    @ManyToOne(fetch = FetchType.LAZY) //On ne met pas CascadeType.ALL car lorsqu'on supprime un lien on ne veut pas supprimer son message
    private Message message;

    public Lien(String adresseLien, String texteLien) {
        this.adresseLien = adresseLien;
        this.texteLien = texteLien;
    }

    public void setMessage(Message message){ this.message = message; }
}
