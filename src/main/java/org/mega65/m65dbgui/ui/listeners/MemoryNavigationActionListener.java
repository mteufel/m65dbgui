package org.mega65.m65dbgui.ui.listeners;

import org.mega65.m65dbgui.events.MemoryNavigationEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class MemoryNavigationActionListener implements ActionListener {

    String to;

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MemoryNavigationEvent event = new MemoryNavigationEvent(to);
        if ( (e.getModifiers()  & ActionEvent.SHIFT_MASK) > 0)
            event.setIncrement(0x1000);
        fireEvent(event);
    }

}
