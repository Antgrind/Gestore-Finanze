package model;

import java.time.LocalDate;

public class RecordContabile {
    private int id;
    private String descrizione;
    private double importo;
    private LocalDate data;
    private String username; // nome utente che ha inserito il record
    private String oggetti;

    // Aggiungi nei costruttori
    public RecordContabile(int id, String descrizione, double importo, LocalDate data, String username, String oggetti) {
        this.id = id;
        this.descrizione = descrizione;
        this.importo = importo;
        this.data = data;
        this.username = username;
        this.oggetti = oggetti;
    }

    public RecordContabile(String descrizione, double importo, LocalDate data, String oggetti) {
        this.descrizione = descrizione;
        this.importo = importo;
        this.data = data;
        this.oggetti = oggetti;
    }

    // Getter
    public String getOggetti() {
        return oggetti;
    }
    // Getters
    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getImporto() {
        return importo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }
}

