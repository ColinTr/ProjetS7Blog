package modele;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idImage;

    @Column(columnDefinition="TEXT") //De base, Hibernate map un String comme un Varchar(255). Pour pouvoir stocker un plus grand String on ajoute cette annotation.
    private String adresseImage;

    @ManyToOne(fetch = FetchType.LAZY) //On ne met pas CascadeType.ALL car lorsqu'on supprime une image on ne veut pas supprimer son message
    private Message message;

    public Image() {
        adresseImage = "";
    }

    public Image(String adresseImage) {
        this.adresseImage = adresseImage;
    }

    public void setMessage(Message msg) {
        message = msg;
    }

    public String getAdresseImage() {
        return adresseImage;
    }
}
