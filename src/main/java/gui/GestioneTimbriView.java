package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.TurnoDAO;
import model.Utente;
import utils.PdfExporter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class GestioneTimbriView extends JPanel {

    private final MainFrame parent;
    private final Utente utente;

    public GestioneTimbriView(MainFrame parent, Utente utente) {
        this.parent = parent;
        this.utente = utente;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 25, 25));

        // Titolo
        JLabel title = new JLabel("🕒 Gestione Timbri", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(255, 204, 128));
        title.setBorder(new EmptyBorder(30, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Sottotitolo
        JLabel subtitle = new JLabel("Benvenuto, " + utente.getUsername(), JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(180, 180, 180));
        subtitle.setBorder(new EmptyBorder(0, 10, 20, 10));
        add(subtitle, BorderLayout.BEFORE_FIRST_LINE);

        // Pulsanti centrati
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        centerPanel.setBackground(new Color(25, 25, 25));
        centerPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        centerPanel.add(createFancyButton("Storico Turni", "icons/history.svg", () -> {
            JFrame frame = new JFrame("Storico Turni");
            frame.setContentPane(new StoricoTurniPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }));

        centerPanel.add(createFancyButton("Esporta Grafico", "icons/chart.svg", () -> {
            List<Object[]> dati = new TurnoDAO().getTotaleOrePerUtente();
            PdfExporter.esportaGraficoOreLavorate(dati);
        }));

        centerPanel.add(createFancyButton("Esporta Ore Totali", "icons/pdf.svg", () -> {
            List<Object[]> dati = new TurnoDAO().getTotaleOrePerUtente();
            PdfExporter.esportaTotaliOre(dati);
        }));

        add(centerPanel, BorderLayout.CENTER);

        // Bottone Indietro
        JButton btnIndietro = new JButton("Indietro", new FlatSVGIcon("icons/back.svg", 16, 16));
        styleButton(btnIndietro);
        btnIndietro.addActionListener(e -> parent.switchView(new HomeViewCapo(parent, utente)));

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(25, 25, 25));
        bottom.add(btnIndietro);
        add(bottom, BorderLayout.SOUTH);
    }

    private JButton createFancyButton(String text, String iconPath, Runnable action) {
        JButton button = new JButton(text, new FlatSVGIcon(iconPath, 24, 24));
        styleButton(button);
        button.addActionListener(e -> action.run());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 112, 67));
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 138, 101), 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 87, 34));
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        });

        return button;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 87, 34));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 60));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
