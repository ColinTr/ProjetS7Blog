package modele;

import javax.persistence.*;
import java.util.List;

@Entity
public class MotCle {

    @Id
    private String motCle;

    @ManyToMany
    @JoinTable(name="MOTCLES_MESSAGE",
            joinColumns={@JoinColumn(name="MOTCLE",
                    referencedColumnName="motCle")},
            inverseJoinColumns={@JoinColumn(name="MESSAGES_ID",
                    referencedColumnName="idMessage")})
    private List<Message> messages;

    public MotCle(String motCle){
        this.motCle = motCle;
    }
}
