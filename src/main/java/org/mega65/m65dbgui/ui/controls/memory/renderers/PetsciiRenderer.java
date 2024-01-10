package org.mega65.m65dbgui.ui.controls.memory.renderers;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.services.FontService;
import org.mega65.m65dbgui.util.ColorHolder;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PetsciiRenderer extends JLabel implements TableCellRenderer {

    FontService fontService;

    @Inject
    public PetsciiRenderer(FontService fontService) {
        this.fontService = fontService;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setOpaque(false);
        setFont(fontService.getMega80());
        setForeground(ColorHolder.getColor(ColorHolder.blue));

        if (value == null) {
            setText(value.toString());
        } else {
            setText(value.toString());
        }
        return this;
    }

}