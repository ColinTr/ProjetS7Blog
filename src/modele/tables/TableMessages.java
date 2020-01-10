package modele.tables;

import javafx.beans.property.SimpleStringProperty;

public class TableMessages {
    private final SimpleStringProperty titreMessage;
    private final SimpleStringProperty adresseMailUtilisateur;
    private final SimpleStringProperty dateMessage;

    public String getTitreMessage() { return titreMessage.get(); }

    public String getAdresseMailUtilisateur() { return adresseMailUtilisateur.get(); }

    public String getDateMessage() { return dateMessage.get(); }

    public TableMessages(String titre, String adresse, String date){
        titreMessage = new SimpleStringProperty(titre);
        adresseMailUtilisateur = new SimpleStringProperty(adresse);
        dateMessage = new SimpleStringProperty(date);
    }
}
