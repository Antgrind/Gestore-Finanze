package dao;

import model.Utente;
import utils.DbConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public Utente login(String username, String password) {
        String sql = "SELECT id, username, ruolo FROM utenti WHERE username = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utente(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("ruolo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean registra(String username, String password, String ruolo) {
        String sql = "INSERT INTO utenti (username, password, ruolo) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, ruolo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Utente> getAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        String sql = "SELECT id, username, ruolo FROM utenti ORDER BY username";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utenti.add(new Utente(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("ruolo")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }


    public boolean eliminaUtente(int idUtente) {
        String checkTurni = "SELECT COUNT(*) FROM turni WHERE id_utente = ?";
        String checkRecords = "SELECT COUNT(*) FROM record_contabili WHERE id_utente = ?";
        String deleteUtente = "DELETE FROM utenti WHERE id = ?";

        try (Connection conn = DbConnection.getConnection()) {
            try (
                    PreparedStatement psTurni = conn.prepareStatement(checkTurni);
                    PreparedStatement psRecords = conn.prepareStatement(checkRecords)
            ) {
                psTurni.setInt(1, idUtente);
                ResultSet rsTurni = psTurni.executeQuery();
                rsTurni.next();
                int turni = rsTurni.getInt(1);

                psRecords.setInt(1, idUtente);
                ResultSet rsRecords = psRecords.executeQuery();
                rsRecords.next();
                int records = rsRecords.getInt(1);

                if (turni > 0 || records > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Impossibile eliminare l'utente: ha ancora " +
                                    (turni > 0 ? turni + " turno/i" : "") +
                                    (turni > 0 && records > 0 ? " e " : "") +
                                    (records > 0 ? records + " record contabile/i" : "") +
                                    " registrati.",
                            "Eliminazione non consentita",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                try (PreparedStatement psDelete = conn.prepareStatement(deleteUtente)) {
                    psDelete.setInt(1, idUtente);
                    return psDelete.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





}
