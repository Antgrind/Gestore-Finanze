package gui;

import dao.RecordContabileDAO;
import model.RecordContabile;
import model.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class RecordsViewScagnozzo extends JPanel implements RecordsViewInterface {

    private Utente utente;
    private RecordContabileDAO dao;
    private JTable table;
    private DefaultTableModel model;
    private MainFrame parent;

    public RecordsViewScagnozzo(MainFrame parent, Utente utente) {
        this.parent = parent;
        this.utente = utente;
        this.dao = new RecordContabileDAO();

        initUI();
        caricaDati();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Table model
        model = new DefaultTableModel(new Object[]{"ID", "Descrizione", "Importo", "Data"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel();

        // Aggiungi icona al bottone "Inserisci Record"
        JButton btnInserisci = new JButton("Attività", new FlatSVGIcon("icons/insert.svg", 16, 16));
        btnInserisci.addActionListener(e -> apriInserimento());

        // Aggiungi icona al bottone "Indietro"
        JButton btnIndietro = new JButton("Indietro", new FlatSVGIcon("icons/back.svg", 16, 16));
        btnIndietro.addActionListener(e -> parent.switchView(new HomeViewScagnozzo(parent, utente)));

        // Aggiungi i bottoni al pannello
        panelBottoni.add(btnInserisci);
        panelBottoni.add(btnIndietro);

        add(panelBottoni, BorderLayout.SOUTH);
    }

    @Override
    public void caricaDati() {
        model.setRowCount(0);

        List<RecordContabile> records = dao.findByUtente(utente.getUsername());

        for (RecordContabile r : records) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getDescrizione(),
                    r.getImporto(),
                    r.getData()
            });
        }
    }

    private void apriInserimento() {
        InserimentoForm form = new InserimentoForm(
                (Frame) SwingUtilities.getWindowAncestor(this), dao, this, utente);
        form.setVisible(true);
    }
}

