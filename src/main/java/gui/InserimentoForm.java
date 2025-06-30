package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.RecordContabileDAO;
import model.RecordContabile;
import model.Utente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InserimentoForm extends JDialog {

    private JTextField txtDescrizione;
    private JTextField txtImporto;
    private JTextField txtData;

    private RecordContabileDAO dao;
    private RecordsViewInterface parentView;
    private Utente utente;

    public InserimentoForm(Frame owner, RecordContabileDAO dao, RecordsViewInterface parentView, Utente utente) {
        super(owner, "Inserisci Nuova Fattura", true);
        this.dao = dao;
        this.parentView = parentView;
        this.utente = utente;

        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

        add(new JLabel("Descrizione:"));
        txtDescrizione = new JTextField();
        add(txtDescrizione);

        add(new JLabel("Importo (€):"));
        txtImporto = new JTextField();
        add(txtImporto);

        add(new JLabel("Data (YYYY-MM-DD):"));
        txtData = new JTextField();
        add(txtData);

        JButton btnSalva = new JButton("Salva", new FlatSVGIcon("icons/save.svg", 16, 16));
        btnSalva.addActionListener(e -> salvaRecord());
        add(btnSalva);

        JButton btnAnnulla = new JButton("Annulla", new FlatSVGIcon("icons/cancel.svg", 16, 16));
        btnAnnulla.addActionListener(e -> dispose());
        add(btnAnnulla);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void salvaRecord() {
        String desc = txtDescrizione.getText().trim();
        String impStr = txtImporto.getText().trim();
        String dataStr = txtData.getText().trim();

        if (desc.isEmpty() || impStr.isEmpty() || dataStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double importo;
        LocalDate data;
        try {
            importo = Double.parseDouble(impStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            data = LocalDate.parse(dataStr);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data non valida. Usa formato YYYY-MM-DD.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RecordContabile record = new RecordContabile(desc, importo, data);
        boolean ok = dao.insertRecord(record, utente.getId());

        if (ok) {
            JOptionPane.showMessageDialog(this, "Record inserito con successo.");
            parentView.caricaDati();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Errore durante l'inserimento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
