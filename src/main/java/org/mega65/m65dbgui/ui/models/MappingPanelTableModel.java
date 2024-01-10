package org.mega65.m65dbgui.ui.models;


import org.mega65.m65dbgui.domain.Mapping;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class MappingPanelTableModel implements TableModel {

    List<Mapping> data = new ArrayList<>();
    Logger logger = Util.getLogger(MappingPanelTableModel.class);

    public MappingPanelTableModel() {
        logger.info("creating");
    }

    public void clean() {
        data = new ArrayList<>();
    }

    public void setData(List<Mapping> data) {
        data.forEach( m -> this.data.add(m));
    }

    public List<Mapping> getData() {
        return data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Type";
            case 1 -> "Source";
            case 2 -> "Start";
            case 3 -> "End";
            case 4 -> "";
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
            case 0: return data.get(rowIndex).mappingType();
            case 1: return Util.friendlyHex(Util.toHex(data.get(rowIndex).sourceAdr(), 7)) + " ->";
            case 2: return "$" + Util.toHex(data.get(rowIndex).startAdr());
            case 3: return "$" + Util.toHex(data.get(rowIndex).endAdr()-1);
            case 4: return data.get(rowIndex).type() + " " +  data.get(rowIndex).text();
            default: return "???";
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {

    }



}
