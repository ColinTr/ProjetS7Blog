package modele;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idImage;

    private String adresseImage;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Message message;

    public Image(String adresseImage) {
        this.adresseImage = adresseImage;
    }

    public void setMessages(Message msg) {
        message = msg;
    }

    public String getAdresseImage() {
        return adresseImage;
    }
}
