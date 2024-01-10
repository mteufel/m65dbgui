package org.mega65.m65dbgui.ui.models;

import org.mega65.m65dbgui.domain.Adressspace;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

import static org.mega65.m65dbgui.util.Util.friendlyHex;

public class AreaTableModel implements TableModel {

    List<Adressspace> adressspaces;

    public void setAdressspaces(List<Adressspace> adressspaces) {
        this.adressspaces = adressspaces;
    }

    @Override
    public int getRowCount() {
        return adressspaces.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Start";
            case 1 -> "End";
            case 2 -> "Available";
            case 3 -> "Description";
            default -> null;
        };
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0: return friendlyHex(this.adressspaces.get(rowIndex).start());
            case 1: return friendlyHex(this.adressspaces.get(rowIndex).end());
            case 2: return this.adressspaces.get(rowIndex).available();
            case 3: return this.adressspaces.get(rowIndex).description();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
