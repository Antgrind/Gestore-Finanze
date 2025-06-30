package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.RecordContabileDAO;
import model.RecordContabile;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RecordsView extends JPanel implements RecordsViewInterface {

    private Utente utente;
    private RecordContabileDAO dao;
    private JTable table;
    private DefaultTableModel model;
    private MainFrame parent;

    public RecordsView(MainFrame parent, Utente utente) {
        this.parent = parent;
        this.utente = utente;
        this.dao = new RecordContabileDAO();

        initUI();
        caricaDati();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Descrizione", "Importo", "Data", "Utente"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel();

        JButton btnInserisci = new JButton("Attività", new FlatSVGIcon("icons/insert.svg", 16, 16));
        btnInserisci.addActionListener(e -> apriInserimento());

        JButton btnIndietro = new JButton("Indietro", new FlatSVGIcon("icons/back.svg", 16, 16));
        btnIndietro.addActionListener(e -> {
            if ("capo".equalsIgnoreCase(utente.getRuolo())) {
                parent.switchView(new HomeViewCapo(parent, utente));
            } else {
                parent.switchView(new HomeViewScagnozzo(parent, utente));
            }
        });

        panelBottoni.add(btnInserisci);
        panelBottoni.add(btnIndietro);

        add(panelBottoni, BorderLayout.SOUTH);
    }

    @Override
    public void caricaDati() {
        model.setRowCount(0);

        List<RecordContabile> records;
        if ("capo".equalsIgnoreCase(utente.getRuolo())) {
            records = dao.findAllConUtente();
        } else {
            records = dao.findByUtente(utente.getUsername());
        }

        for (RecordContabile r : records) {
            model.addRow(new Object[] {
                    r.getId(),
                    r.getDescrizione(),
                    r.getImporto(),
                    r.getData(),
                    r.getUsername()
            });
        }
    }

    private void apriInserimento() {
        InserimentoForm form = new InserimentoForm(
                (Frame) SwingUtilities.getWindowAncestor(this), dao, this, utente);
        form.setVisible(true);
    }
}

