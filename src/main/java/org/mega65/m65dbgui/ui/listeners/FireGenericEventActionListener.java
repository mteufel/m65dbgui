package org.mega65.m65dbgui.ui.listeners;

import org.mega65.m65dbgui.events.GenericEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class FireGenericEventActionListener implements ActionListener {

    String event;

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEvent(new GenericEvent(event));
    }

    public void setEvent(String event) {
        this.event = event;
    }
}