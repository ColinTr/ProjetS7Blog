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

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date date;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lien> liens;

    @ManyToMany(mappedBy = "messages", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MotCle> motCles;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Utilisateur utilisateur;

}
