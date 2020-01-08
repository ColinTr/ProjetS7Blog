package modele;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMessage;

    private String titre;
    private String texte;

    @ElementCollection
    @CollectionTable(name="Images", joinColumns=@JoinColumn(name="idMessage"))
    @Column(name="images")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name="Liens", joinColumns=@JoinColumn(name="idMessage"))
    @Column(name="liens")
    private List<String> liens;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Utilisateur utilisateur;

}
