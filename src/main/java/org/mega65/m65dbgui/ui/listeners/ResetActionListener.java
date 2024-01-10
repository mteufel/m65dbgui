package org.mega65.m65dbgui.ui.listeners;

import org.mega65.m65dbgui.operations.M65Reset;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class ResetActionListener  implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEvent(new M65Reset());
    }
}
