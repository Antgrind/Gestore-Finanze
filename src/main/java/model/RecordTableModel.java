package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RecordTableModel extends AbstractTableModel {

    private final String[] columns = {"Descrizione", "Importo", "Data", "Autore"};
    private List<RecordContabile> records = new ArrayList<>();

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        RecordContabile r = records.get(row);
        return switch (col) {
            case 0 -> r.getDescrizione();
            case 1 -> r.getImporto();
            case 2 -> r.getData();
            case 3 -> r.getUsername();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setRecords(List<RecordContabile> records) {
        this.records = records;
        fireTableDataChanged();
    }
}

