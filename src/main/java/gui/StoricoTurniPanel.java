package gui;

import dao.TurnoDAO;
import utils.PdfExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StoricoTurniPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public StoricoTurniPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Username", "Inizio", "Fine", "Minuti"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottoni = new JPanel();

        JButton btnRicarica = new JButton("Ricarica");
        btnRicarica.addActionListener(e -> caricaDati());

        JButton btnPdf = new JButton("Esporta PDF");
        btnPdf.addActionListener(e -> PdfExporter.esportaStorico(model));

        bottoni.add(btnRicarica);
        bottoni.add(btnPdf);

        add(bottoni, BorderLayout.SOUTH);

        caricaDati();
    }

    private void caricaDati() {
        model.setRowCount(0);
        List<Object[]> righe = new TurnoDAO().getStoricoTurniCompletati();
        for (Object[] r : righe) {
            model.addRow(r);
        }
    }
}

