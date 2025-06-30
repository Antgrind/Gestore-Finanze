package gui;

import dao.TurnoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TurniAttiviPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public TurniAttiviPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID Utente", "Username", "Inizio Turno"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAggiorna = new JButton("Aggiorna");
        btnAggiorna.addActionListener(e -> caricaDati());
        add(btnAggiorna, BorderLayout.SOUTH);

        caricaDati();
    }

    private void caricaDati() {
        model.setRowCount(0);
        TurnoDAO dao = new TurnoDAO();
        List<Object[]> turni = dao.getTurniAttiviConUtente();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Object[] row : turni) {
            model.addRow(new Object[]{
                    row[0],
                    row[1],
                    ((LocalDateTime) row[2]).format(fmt)
            });
        }
    }
}

