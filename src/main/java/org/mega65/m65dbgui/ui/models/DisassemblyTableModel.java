package org.mega65.m65dbgui.ui.models;

import jakarta.annotation.PostConstruct;
import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class DisassemblyTableModel implements TableModel {

    List<Disassembly> disassembly;

    public DisassemblyTableModel() {

    }

    @PostConstruct
    public void postConstruct() {
        this.disassembly = new ArrayList<>();
    }

    public void setDisassembly(List<Disassembly> disassembly) {
        this.disassembly = disassembly;
    }

    @Override
    public int getRowCount() {
        return disassembly.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "colAdress";
            case 1: return "colOpCode";
            case 2: return "colOp1";
            case 3: return "colOp2";
            case 4: return "colInstr1";
            case 5: return "colInstr2";
            case 6: return "colCycles";
            case 7: return "colAdressing";
            default: return null;
        }
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

        Disassembly dis = disassembly.get(rowIndex);

        switch (columnIndex) {
            case 0: return Util.toHex(dis.adr());
            case 1: return dis.opcode().getOp();
            case 2: return Util.toHex(Util.checkLong(dis.operand1()));
            case 3: return Util.toHex(Util.checkLong(dis.operand2()));
            case 4: return dis.opcode().getInstruction();    // the instruction itself
            case 5: return dis.opcode().getAdressing().getInstruction("$", dis);  // and its operand(s) according to its addressing
            case 6: return dis.opcode().getCycles();
            case 7: return dis.opcode().getAdressing().getShortcut1();
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
