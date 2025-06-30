package gui;

import dao.RecordContabileDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StatisticheUtentiPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private RecordContabileDAO dao = new RecordContabileDAO();

    public StatisticheUtentiPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Username", "Numero Record", "Totale Fatturato (€)"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottoni = new JPanel();

        JButton btnRicarica = new JButton("Ricarica");
        btnRicarica.addActionListener(e -> caricaDati());

        JButton btnAzzera = new JButton("Azzera Selezionato");
        btnAzzera.addActionListener(e -> azzeraSelezionato());

        bottoni.add(btnRicarica);
        bottoni.add(btnAzzera);

        add(bottoni, BorderLayout.SOUTH);

        caricaDati();
    }

    private void caricaDati() {
        model.setRowCount(0);
        List<Object[]> righe = dao.getStatistichePerUtente();
        for (Object[] r : righe) {
            model.addRow(r);
        }
    }

    private void azzeraSelezionato() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un utente.");
            return;
        }

        String username = (String) model.getValueAt(row, 0);
        int conferma = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler azzerare le statistiche per \"" + username + "\"?",
                "Conferma", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            boolean ok = dao.azzeraStatisticheUtente(username);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Statistiche azzerate.");
                caricaDati();
            } else {
                JOptionPane.showMessageDialog(this, "Errore durante l'operazione.");
            }
        }
    }
}
