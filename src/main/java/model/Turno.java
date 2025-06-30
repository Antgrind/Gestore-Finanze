package model;

import java.time.LocalDateTime;

public class Turno {
    private int id;
    private int idUtente;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    private Integer minutiServizio;

    public Turno(int id, int idUtente, LocalDateTime inizio, LocalDateTime fine, Integer minutiServizio) {
        this.id = id;
        this.idUtente = idUtente;
        this.inizio = inizio;
        this.fine = fine;
        this.minutiServizio = minutiServizio;
    }

    public Turno(int idUtente, LocalDateTime inizio) {
        this.idUtente = idUtente;
        this.inizio = inizio;
    }

    public int getId() { return id; }
    public int getIdUtente() { return idUtente; }
    public LocalDateTime getInizio() { return inizio; }
    public LocalDateTime getFine() { return fine; }
    public Integer getMinutiServizio() { return minutiServizio; }
}

