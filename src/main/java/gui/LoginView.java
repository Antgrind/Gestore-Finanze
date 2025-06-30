package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.UtenteDAO;
import model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JPanel {

    private MainFrame parent;

    public LoginView(MainFrame parent) {
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(18, 18, 18)); // dark background

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(38, 38, 38, 230)); // semi trasparente
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 87, 34), 2),
                new EmptyBorder(30, 50, 30, 50)
        ));

        JLabel title = new JLabel("MAFIA MANAGER", new FlatSVGIcon("icons/logo-mafia.svg", 24, 24), JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);
        card.add(Box.createVerticalStrut(30));

        // Username
        JLabel lblUser = new JLabel("Username", new FlatSVGIcon("icons/user.svg", 16, 16), JLabel.LEFT);
        styleLabel(lblUser);
        JTextField txtUser = new JTextField(15);
        styleField(txtUser);

        // Password
        JLabel lblPass = new JLabel("Password", new FlatSVGIcon("icons/lock.svg", 16, 16), JLabel.LEFT);
        styleLabel(lblPass);
        JPasswordField txtPass = new JPasswordField(15);
        styleField(txtPass);

        // Add fields
        card.add(lblUser);
        card.add(Box.createVerticalStrut(5));
        card.add(txtUser);
        card.add(Box.createVerticalStrut(15));
        card.add(lblPass);
        card.add(Box.createVerticalStrut(5));
        card.add(txtPass);
        card.add(Box.createVerticalStrut(25));

        // Button
        JButton btnLogin = new JButton("Entra nella Famiglia", new FlatSVGIcon("icons/login.svg", 18, 18));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(255, 87, 34));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.putClientProperty("JButton.buttonType", "roundRect");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword()).trim();

            UtenteDAO dao = new UtenteDAO();
            Utente u = dao.login(username, password);
            if (u != null) {
                parent.loginEffettuato(u);
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(btnLogin);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(250, 30));
        field.setBackground(new Color(55, 71, 79));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
    }

    private void styleLabel(JLabel label) {
        label.setForeground(new Color(220, 220, 220));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

