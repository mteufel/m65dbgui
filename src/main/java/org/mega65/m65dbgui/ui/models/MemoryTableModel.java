package org.mega65.m65dbgui.ui.models;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.operations.M65Store;
import org.mega65.m65dbgui.services.FontService;
import org.mega65.m65dbgui.services.MemoryService;
import org.mega65.m65dbgui.ui.controls.memory.MemoryTable;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class MemoryTableModel implements TableModel {

    FontService fontService;
    MemoryService memoryService;
    State state;
    List<Byte> bytes = new ArrayList<>();
    Logger logger = Util.getLogger(MemoryTableModel.class);

    @Inject
    public MemoryTableModel(State state, FontService fontService, MemoryService memoryService) {
        this.state = state;
        this.fontService = fontService;
        this.memoryService = memoryService;
    }

    public void clean() {
        bytes = new ArrayList<>();
    }

    public void addByte(Byte by) {
        bytes.add(by);
    }


    @Override
    public int getRowCount() {
        double a = Double.valueOf(bytes.size());
        double b = Double.valueOf(16);
        double v = a/ b;
        int rowCount =  (int) Math.ceil(v);
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return 19;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Addr";
            case 1: return "1";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            case 11: return "11";
            case 12: return "12";
            case 13: return "13";
            case 14: return "14";
            case 15: return "15";
            case 16: return "16";
            case 17: return "-";
            case 18: return "Value";
            default: return null;
        }
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Long.class;
            case 17:
            case 18: return String.class;
            default: return Byte.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex >= 1 && columnIndex <= 16) {
            return true;
        }
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (!bytes.isEmpty()) {
            final long startAddr = bytes.get(0).getAddr() + (rowIndex * 16);

            switch (columnIndex) {
                case 0: return Util.toHex(startAddr, 4);
                case 1: return getByteByAddr(startAddr);
                case 2: return getByteByAddr(startAddr+1);
                case 3: return getByteByAddr(startAddr+2);
                case 4: return getByteByAddr(startAddr+3);
                case 5: return getByteByAddr(startAddr+4);
                case 6: return getByteByAddr(startAddr+5);
                case 7: return getByteByAddr(startAddr+6);
                case 8: return getByteByAddr(startAddr+7);
                case 9: return getByteByAddr(startAddr+8);
                case 10: return getByteByAddr(startAddr+9);
                case 11: return getByteByAddr(startAddr+10);
                case 12: return getByteByAddr(startAddr+11);
                case 13: return getByteByAddr(startAddr+12);
                case 14: return getByteByAddr(startAddr+13);
                case 15: return getByteByAddr(startAddr+14);
                case 16: return getByteByAddr(startAddr+15);
                case 17: return "";
                case 18: return createPetsciiString(startAddr);
                default: return -1;
            }
        }
        return null;
    }

    private Byte getByteByAddr(long addr) {
        try {
            return bytes.stream().filter( b -> addr == b.getAddr() ).findFirst().get();
        } catch (NoSuchElementException e) {
        }
        return null;
    }



    private String createPetsciiString(long addr) {
        String result = "";
        Long value;
        for (int i = 0; i < 16; i++) {
            Byte by = getByteByAddr(addr + i);
            if (by == null ) {
              value = Long.valueOf(0x2e);   // wenn null dann . rendern
            } else {
                value = by.getValue();
            }
            result = result + fontService.getPetscii(value);
        }
        return result;
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (columnIndex >= 1 && columnIndex <= 16) {

            String entry = (String) aValue;

            if (Util.isHex(entry)) {
                state.setPause(false);
                String startAddr = Util.toHex(bytes.get(0).getAddr() + (rowIndex * 16) + (columnIndex-1));
                memoryService.poke(Byte.parseByte(startAddr, entry));
                DiUtil.fireEvent(new GenericEvent(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH));

            }


        }


    }


    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

}
