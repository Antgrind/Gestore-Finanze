package model;

public class Utente {
    private int id;
    private String username;
    private String ruolo;

    public Utente(int id, String username, String ruolo) {
        this.id = id;
        this.username = username;
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRuolo() {
        return ruolo;
    }

    public boolean isCapo() {
        return "capo".equalsIgnoreCase(ruolo);
    }
}

