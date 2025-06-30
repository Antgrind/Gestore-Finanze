package utils;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;


public class PdfExporter {

    public static void esportaStorico(TableModel model) {
        try {
            // Percorso desktop dell'utente
            String userHome = System.getProperty("user.home");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
            String fileName = "storico_turni_" + timestamp + ".pdf";
            String fullPath = userHome + "/Desktop/" + fileName;

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fullPath));
            doc.open();

            doc.add(new Paragraph("Storico Turni Completati"));
            doc.add(new Paragraph("Generato il: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(model.getColumnCount());
            for (int i = 0; i < model.getColumnCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(model.getColumnName(i))));
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    table.addCell(value != null ? value.toString() : "");
                }
            }

            doc.add(table);
            doc.close();

            javax.swing.JOptionPane.showMessageDialog(null, "PDF esportato sul Desktop:\n" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Errore durante l'esportazione PDF.");
        }
    }

    public static void esportaTotaliOre(List<Object[]> dati) {
        try {
            String userHome = System.getProperty("user.home");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
            String fileName = "riepilogo_ore_lavorate_" + timestamp + ".pdf";
            String fullPath = userHome + "/Desktop/" + fileName;

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(fullPath));
            doc.open();

            doc.add(new Paragraph("Riepilogo Ore Lavorate per Utente"));
            doc.add(new Paragraph("Generato il: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.addCell("Nome Utente");
            table.addCell("Tempo Totale");

            for (Object[] row : dati) {
                String username = (String) row[0];
                int minuti = (int) row[1];
                int ore = minuti / 60;
                int restantiMinuti = minuti % 60;

                String tempoFormattato = ore + " ore " + restantiMinuti + " minuti";

                table.addCell(username);
                table.addCell(tempoFormattato);
            }

            doc.add(table);
            doc.close();

            JOptionPane.showMessageDialog(null, "PDF esportato sul Desktop:\n" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante l'esportazione PDF.");
        }
    }

    public static void esportaGraficoOreLavorate(List<Object[]> dati) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Object[] row : dati) {
                String username = (String) row[0];
                int minuti = (int) row[1];
                double ore = minuti / 60.0;
                dataset.addValue(ore, "Ore", username);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Ore Lavorate per Utente",
                    "Utente",
                    "Ore",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false
            );

            String userHome = System.getProperty("user.home");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
            String fileName = "grafico_ore_lavorate_" + timestamp + ".png";
            String fullPath = userHome + "/Desktop/" + fileName;

            ChartUtils.saveChartAsPNG(new File(fullPath), chart, 800, 600);

            JOptionPane.showMessageDialog(null, "Grafico esportato sul Desktop:\n" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante l'esportazione del grafico.");
        }
    }


}
