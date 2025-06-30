package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.TurnoDAO;
import model.Utente;
import utils.PdfExporter;
import java.util.List;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeViewCapo extends JPanel {

    private final MainFrame parent;
    private final Utente utente;

    public HomeViewCapo(MainFrame parent, Utente utente) {
        this.parent = parent;
        this.utente = utente;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));

        JLabel title = new JLabel("Benvenuto, Capo " + utente.getUsername(), JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(30, 10, 30, 10));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(1, 3, 30, 0));
        panel.setBackground(new Color(20, 20, 20));
        panel.setBorder(new EmptyBorder(10, 40, 40, 40));

        panel.add(createButton("Gestione Attività", "icons/records.svg", e ->
                parent.switchView(new RecordsView(parent, utente))
        ));

        panel.add(createButton("Gestione Timbri", "icons/clock.svg", e ->
                parent.switchView(new GestioneTimbriView(parent, utente))
        ));

        panel.add(createButton("Statistiche Utenti", "icons/stats.svg", e -> {
            JFrame frame = new JFrame("Statistiche Utenti");
            frame.setContentPane(new StatisticheUtentiPanel());
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }));

        panel.add(createButton("Visualizza Utenti", "icons/list.svg", e -> {
            JFrame frame = new JFrame("Utenti Registrati");
            frame.setContentPane(new VisualizzaUtentiPanel());
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }));



        panel.add(createButton("Gestione Utenti", "icons/users.svg", e -> {
            GestioneUtentiView gestioneUtentiView = new GestioneUtentiView();
            gestioneUtentiView.setVisible(true);
        }));

        panel.add(createButton("Logout", "icons/logout.svg", e ->
                parent.switchView(new LoginView(parent))
        ));

        add(panel, BorderLayout.CENTER);

        // Aggiunta della tabella dei turni attivi sotto i bottoni
        JPanel turniPanel = new JPanel(new BorderLayout());
        turniPanel.setBackground(new Color(30, 30, 30));
        turniPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Turni Attivi", 0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.WHITE));

        TurniAttiviPanel tabellaTurni = new TurniAttiviPanel();
        turniPanel.add(tabellaTurni, BorderLayout.CENTER);
        add(turniPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, String iconPath, ActionListener listener) {
        JButton btn = new JButton(text, new FlatSVGIcon(iconPath, 24, 24));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(255, 87, 34));
        btn.setForeground(Color.WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.addActionListener(listener);
        return btn;
    }
}
