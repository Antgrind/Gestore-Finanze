package model;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Applica il tema prima di qualsiasi componente
            FlatMacDarkLaf.setup();

            // Imposta font e colori globali
            FlatLaf.setGlobalExtraDefaults(Map.of(
                    "@accentColor", "#FF5722",
                    "@foreground", "#ECEFF1",
                    "@background", "#263238",
                    "@fontFamily", "Segoe UI",
                    "@fontSize", "15"
            ));

            // Imposta il font di default per tutti i componenti Swing
            UIManager.put("defaultFont", new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 15));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Avvia l'interfaccia grafica
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

}
