package dao;

import model.RecordContabile;
import utils.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordContabileDAO {

    // Inserisce un record collegato all'utente tramite id_utente
    public boolean insertRecord(RecordContabile record, int idUtente) {
        String sql = "INSERT INTO record_contabili (descrizione, importo, data, id_utente) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, record.getDescrizione());
            ps.setDouble(2, record.getImporto());
            ps.setDate(3, Date.valueOf(record.getData()));
            ps.setInt(4, idUtente);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Restituisce tutti i record con username associato (per capo)
    public List<RecordContabile> findAllConUtente() {
        List<RecordContabile> records = new ArrayList<>();
        String sql = "SELECT r.id, r.descrizione, r.importo, r.data, u.username " +
                "FROM record_contabili r JOIN utenti u ON r.id_utente = u.id " +
                "ORDER BY r.data DESC";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RecordContabile record = new RecordContabile(
                        rs.getInt("id"),
                        rs.getString("descrizione"),
                        rs.getDouble("importo"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("username")
                );
                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Restituisce solo i record creati da uno specifico username (per scagnozzo)
    public List<RecordContabile> findByUtente(String username) {
        List<RecordContabile> records = new ArrayList<>();
        String sql = "SELECT r.id, r.descrizione, r.importo, r.data, u.username " +
                "FROM record_contabili r JOIN utenti u ON r.id_utente = u.id " +
                "WHERE u.username = ? ORDER BY r.data DESC";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecordContabile record = new RecordContabile(
                            rs.getInt("id"),
                            rs.getString("descrizione"),
                            rs.getDouble("importo"),
                            rs.getDate("data").toLocalDate(),
                            rs.getString("username")
                    );
                    records.add(record);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}

