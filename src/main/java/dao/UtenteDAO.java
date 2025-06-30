package dao;

import model.Utente;
import utils.DbConnection;

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
        String sql = "DELETE FROM utenti WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
