package gui;

import model.Utente;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Mafia Manager");

        // 🔥 Imposta la finestra a schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // Lascia la barra del titolo visibile

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Imposta sfondo coerente con FlatLaf Dark
        getContentPane().setBackground(new Color(20, 20, 20));

        // View iniziale
        switchView(new LoginView(this));

        setVisible(true); // Assicurati che venga mostrata
    }


    public void switchView(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }

    public void loginEffettuato(Utente utente) {
        if (utente.isCapo()) {
            switchView(new HomeViewCapo(this, utente));
        } else {
            switchView(new HomeViewScagnozzo(this, utente));
        }
    }

}

