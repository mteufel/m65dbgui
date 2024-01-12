package org.mega65.m65dbgui.ui.models;


import jakarta.inject.Singleton;
import org.mega65.m65dbgui.domain.Mapping;
import org.mega65.m65dbgui.domain.Register;
import org.mega65.m65dbgui.services.RegistersService;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class RegisterDescriptionModel implements TableModel {

   Register register;
   Long value;
   Logger logger = Util.getLogger(RegisterDescriptionModel.class);

    public void setRegister(Register register) {
        this.register=register;
    }
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public int getRowCount() {
        return 8;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Bit";
            case 1 -> "Identifier";
            case 2 -> "Description";
            default -> null;
        };
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex==0)
            return Long.class;
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return value;
            case 1: return getIdentifier(rowIndex);
            case 2: return getDescription(rowIndex);
            default: return "???";
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


    private String getIdentifier(int rowIndex) {
        if (register == null)
            return "";

        switch (rowIndex) {
            case 0:
                return register.db7();
            case 1:
                return register.db6();
            case 2:
                return register.db5();
            case 3:
                return register.db4();
            case 4:
                return register.db3();
            case 5:
                return register.db2();
            case 6:
                return register.db1();
            case 7:
                return register.db0();
            default:
                return "";
        }
    }

    private String getDescription(int rowIndex) {
        if (register == null)
            return "";

        switch (rowIndex) {
            case 0:
                return register.description().get(register.db7());
            case 1:
                return register.description().get(register.db6());
            case 2:
                return register.description().get(register.db5());
            case 3:
                return register.description().get(register.db4());
            case 4:
                return register.description().get(register.db3());
            case 5:
                return register.description().get(register.db2());
            case 6:
                return register.description().get(register.db1());
            case 7:
                return register.description().get(register.db0());
            default:
                return "";
        }
    }



}
