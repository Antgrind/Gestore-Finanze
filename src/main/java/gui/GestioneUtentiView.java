package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.UtenteDAO;

import javax.swing.*;
import java.awt.*;

public class GestioneUtentiView extends JFrame {
    public GestioneUtentiView() {
        setTitle("Registrazione Nuovo Utente");
        setLayout(new GridLayout(4, 2, 10, 10));

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JComboBox<String> comboRuolo = new JComboBox<>(new String[]{"capo", "scagnozzo"});

        // Modifica del percorso per le icone
        add(new JLabel("Username:", new FlatSVGIcon("icons/user.svg", 16, 16), JLabel.LEFT));
        add(txtUser);
        add(new JLabel("Password:", new FlatSVGIcon("icons/lock.svg", 16, 16), JLabel.LEFT));
        add(txtPass);
        add(new JLabel("Ruolo:", new FlatSVGIcon("icons/role.svg", 16, 16), JLabel.LEFT));
        add(comboRuolo);

        // Bottone con icona
        JButton btnRegistra = new JButton("Registra", new FlatSVGIcon("icons/register.svg", 16, 16));
        btnRegistra.addActionListener(e -> {
            boolean ok = new UtenteDAO().registra(txtUser.getText(), new String(txtPass.getPassword()), (String) comboRuolo.getSelectedItem());
            JOptionPane.showMessageDialog(this, ok ? "Utente registrato" : "Errore");
        });

        add(btnRegistra);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
