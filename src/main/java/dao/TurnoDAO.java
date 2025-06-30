package dao;

import model.Turno;
import utils.DbConnection;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    public boolean iniziaTurno(int idUtente) {
        if (haTurnoAttivo(idUtente)) return false;

        String sql = "INSERT INTO turni (id_utente, inizio) VALUES (?, now())";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean terminaTurno(int idUtente) {
        String select = "SELECT id, inizio FROM turni WHERE id_utente = ? AND fine IS NULL";
        String update = "UPDATE turni SET fine = now(), minuti_servizio = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement psSelect = conn.prepareStatement(select)) {

            psSelect.setInt(1, idUtente);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int idTurno = rs.getInt("id");
                LocalDateTime inizio = rs.getTimestamp("inizio").toLocalDateTime();
                int minuti = (int) Duration.between(inizio, LocalDateTime.now()).toMinutes();

                try (PreparedStatement psUpdate = conn.prepareStatement(update)) {
                    psUpdate.setInt(1, minuti);
                    psUpdate.setInt(2, idTurno);
                    return psUpdate.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean haTurnoAttivo(int idUtente) {
        String sql = "SELECT COUNT(*) FROM turni WHERE id_utente = ? AND fine IS NULL";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getTurniAttiviConUtente() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT t.id_utente, u.username, t.inizio
            FROM turni t
            JOIN utenti u ON t.id_utente = u.id
            WHERE t.fine IS NULL
            ORDER BY t.inizio
        """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id_utente"),
                        rs.getString("username"),
                        rs.getTimestamp("inizio").toLocalDateTime()
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Object[]> getStoricoTurniCompletati() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
        SELECT u.username, t.inizio, t.fine, t.minuti_servizio
        FROM turni t
        JOIN utenti u ON t.id_utente = u.id
        WHERE t.fine IS NOT NULL
        ORDER BY t.fine DESC
    """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getString("username"),
                        rs.getTimestamp("inizio").toLocalDateTime(),
                        rs.getTimestamp("fine").toLocalDateTime(),
                        rs.getInt("minuti_servizio")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Object[]> getTotaleOrePerUtente() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
        SELECT u.username, SUM(t.minuti_servizio) AS totale_minuti
        FROM turni t
        JOIN utenti u ON t.id_utente = u.id
        WHERE t.fine IS NOT NULL
        GROUP BY u.username
        ORDER BY totale_minuti DESC
    """;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                int minuti = rs.getInt("totale_minuti");
                lista.add(new Object[]{username, minuti});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


}
