package modele;

import javax.persistence.*;

@Entity
public class Lien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLien;

    private String adresseLien;
    private String texteLien;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Message message;

    public Lien(String adresseLien, String texteLien) {
        this.adresseLien = adresseLien;
        this.texteLien = texteLien;
    }

    public void setMessage(Message message){ this.message = message; }
}
