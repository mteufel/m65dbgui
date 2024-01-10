package org.mega65.m65dbgui.ui.controls.memory.renderers;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.services.FontService;
import org.mega65.m65dbgui.util.ColorHolder;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class AddressRenderer extends DefaultTableCellRenderer {

    FontService fontService;

    @Inject
    public AddressRenderer(FontService fontService) {
        this.fontService = fontService;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setFont(fontService.getMonoSmallFont());
        setForeground(ColorHolder.getColor(ColorHolder.green));

        if (value == null) {
            setText(value.toString());
        } else {
            setText(value.toString());
        }


        return this;
    }

}