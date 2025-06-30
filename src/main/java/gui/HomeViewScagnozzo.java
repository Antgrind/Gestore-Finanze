package gui;

import model.Utente;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class HomeViewScagnozzo extends JPanel {
    private MainFrame parent;
    private Utente utente;

    public HomeViewScagnozzo(MainFrame parent, Utente utente) {
        this.parent = parent;
        this.utente = utente;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JLabel welcome = new JLabel("Benvenuto utente: " + utente.getUsername(), SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(Color.WHITE);
        welcome.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(welcome, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 20, 20)); // Aggiunto un terzo bottone
        buttonsPanel.setBackground(new Color(30, 30, 30));

        // Bottone Gestione Attività
        JButton btnGestione = createButton("Gestione Attività", "icons/records.svg");
        btnGestione.addActionListener(e -> parent.switchView(new RecordsViewScagnozzo(parent, utente)));
        buttonsPanel.add(btnGestione);

        // Bottone Timbratura
        JButton btnTimbra = createButton("Timbra Servizio", "icons/clock.svg");
        btnTimbra.addActionListener(e -> {
            TimbraturaDialog dialog = new TimbraturaDialog((Frame) SwingUtilities.getWindowAncestor(this), utente);
            dialog.setVisible(true);
        });
        buttonsPanel.add(btnTimbra);

        // Bottone Logout
        JButton btnLogout = createButton("Logout", "icons/logout.svg");
        btnLogout.addActionListener(e -> parent.switchView(new LoginView(parent)));
        buttonsPanel.add(btnLogout);

        add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text, new FlatSVGIcon(iconPath, 18, 18));
        styleButton(button);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 87, 34));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(55, 71, 79));
            }
        });
        return button;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(55, 71, 79));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 87, 34), 2, true));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 50));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
    }
}
