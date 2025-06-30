package model;

import java.time.LocalDate;

public class RecordContabile {
    private int id;
    private String descrizione;
    private double importo;
    private LocalDate data;
    private String username; // nome utente che ha inserito il record

    // Costruttore usato per leggere dal DB (con id e username)
    public RecordContabile(int id, String descrizione, double importo, LocalDate data, String username) {
        this.id = id;
        this.descrizione = descrizione;
        this.importo = importo;
        this.data = data;
        this.username = username;
    }

    // Costruttore usato per inserire nuovo record (senza id e username)
    public RecordContabile(String descrizione, double importo, LocalDate data) {
        this.descrizione = descrizione;
        this.importo = importo;
        this.data = data;
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

