package modele;

import javax.persistence.*;
import java.util.List;

@Entity
public class Lien {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLien;

    private String adresseLien;
    private String texteLien;

    @ManyToMany
    @JoinTable(name="LIEN_MESSAGE",
            joinColumns={@JoinColumn(name="LIENS_ID",
                    referencedColumnName="idLien")},
            inverseJoinColumns={@JoinColumn(name="MESSAGES_ID",
                    referencedColumnName="idMessage")})
    private List<Message> messages;
}
