package gui;

import dao.TurnoDAO;
import model.Utente;

import javax.swing.*;
import java.awt.*;

public class TimbraturaDialog extends JDialog {

    private final TurnoDAO turnoDAO = new TurnoDAO();
    private final Utente utente;

    public TimbraturaDialog(Frame owner, Utente utente) {
        super(owner, "Timbratura Servizio", true);
        this.utente = utente;
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(3, 1, 10, 10));
        setSize(300, 150);
        setLocationRelativeTo(getOwner());

        JButton btnInizio = new JButton("Inizia Turno");
        JButton btnFine = new JButton("Termina Turno");
        JButton btnAnnulla = new JButton("Annulla");

        btnInizio.addActionListener(e -> {
            if (turnoDAO.iniziaTurno(utente.getId())) {
                JOptionPane.showMessageDialog(this, "Turno iniziato!");
            } else {
                JOptionPane.showMessageDialog(this, "Hai già un turno attivo!");
            }
            dispose();
        });

        btnFine.addActionListener(e -> {
            if (turnoDAO.terminaTurno(utente.getId())) {
                JOptionPane.showMessageDialog(this, "Turno terminato!");
            } else {
                JOptionPane.showMessageDialog(this, "Nessun turno attivo trovato!");
            }
            dispose();
        });

        btnAnnulla.addActionListener(e -> dispose());

        add(btnInizio);
        add(btnFine);
        add(btnAnnulla);
    }
}
