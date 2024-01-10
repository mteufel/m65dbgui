package org.mega65.m65dbgui.ui.controls.memory.renderers;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.services.FontService;
import org.mega65.m65dbgui.util.ColorHolder;
import org.mega65.m65dbgui.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ByteRenderer  extends DefaultTableCellRenderer {

    FontService fontService;

    @Inject
    public ByteRenderer(FontService fontService) {
        this.fontService = fontService;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setFont(fontService.getMonoSmallFont());
        setForeground(ColorHolder.getColor(ColorHolder.lightGrey));

        if (value instanceof Byte) {
            Byte by = (Byte) value;
            setText(Util.toHex(by.getValue()));
            setToolTipText(getHtmlForTooltip(by));
        } else {
            setText(Util.toHex(0));
        }

        return this;
    }

    private String getHtmlForTooltip(Byte by) {
        return "<html>$" + Util.toHex(by.getAddr(), 4) + "<br>Hex: $" + Util.toHex(by.getValue()) + "<br>Decimal: " + by.getValue() + "<br>Binary: %" + Util.toBinary(by.getValue())  + "</html>";
    }


}