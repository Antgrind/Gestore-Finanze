package gui;

import dao.UtenteDAO;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VisualizzaUtentiPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private UtenteDAO dao = new UtenteDAO();

    public VisualizzaUtentiPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Username", "Ruolo"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton btnRicarica = new JButton("Ricarica");
        btnRicarica.addActionListener(e -> caricaDati());

        JButton btnElimina = new JButton("Elimina Selezionato");
        btnElimina.addActionListener(e -> eliminaUtente());

        buttons.add(btnRicarica);
        buttons.add(btnElimina);

        add(buttons, BorderLayout.SOUTH);

        caricaDati();
    }

    private void caricaDati() {
        model.setRowCount(0);
        List<Utente> utenti = dao.getAllUtenti();
        for (Utente u : utenti) {
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getRuolo()});
        }
    }

    private void eliminaUtente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un utente da eliminare.");
            return;
        }

        int idUtente = (int) model.getValueAt(selectedRow, 0);
        String username = (String) model.getValueAt(selectedRow, 1);

        int conferma = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler eliminare l'utente \"" + username + "\"?",
                "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            if (dao.eliminaUtente(idUtente)) {
                JOptionPane.showMessageDialog(this, "Utente eliminato.");
                caricaDati();
            } else {
                JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione.");
            }
        }
    }
}
