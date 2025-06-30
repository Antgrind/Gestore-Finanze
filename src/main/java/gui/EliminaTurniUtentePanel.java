package gui;

import dao.TurnoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EliminaTurniUtentePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TurnoDAO dao = new TurnoDAO();

    public EliminaTurniUtentePanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Username", "Numero Turni"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnElimina = new JButton("Elimina Turni Selezionato");
        btnElimina.addActionListener(e -> eliminaTurni());

        JButton btnRicarica = new JButton("Ricarica");
        btnRicarica.addActionListener(e -> caricaDati());

        JPanel bottoni = new JPanel();
        bottoni.add(btnRicarica);
        bottoni.add(btnElimina);

        add(bottoni, BorderLayout.SOUTH);

        caricaDati();
    }

    private void caricaDati() {
        model.setRowCount(0);
        List<Object[]> righe = dao.getConteggioTurniPerUtente();
        for (Object[] r : righe) {
            model.addRow(r);
        }
    }

    private void eliminaTurni() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un utente.");
            return;
        }

        String username = (String) model.getValueAt(row, 0);
        int conferma = JOptionPane.showConfirmDialog(this,
                "Eliminare tutti i turni per \"" + username + "\"?",
                "Conferma", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            boolean ok = dao.eliminaTurniPerUtente(username);
            JOptionPane.showMessageDialog(this,
                    ok ? "Turni eliminati." : "Errore o nessun turno da eliminare.");
            caricaDati();
        }
    }
}
