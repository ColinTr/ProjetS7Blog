package modele;

import javax.persistence.*;
import java.util.List;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idImage;

    private String adresseImage;

    @ManyToMany
    @JoinTable(name="IMAGE_MESSAGE",
            joinColumns={@JoinColumn(name="IMAGES_ID",
                    referencedColumnName="idImage")},
            inverseJoinColumns={@JoinColumn(name="MESSAGES_ID",
                    referencedColumnName="idMessage")})
    private List<Message> messages;
}
