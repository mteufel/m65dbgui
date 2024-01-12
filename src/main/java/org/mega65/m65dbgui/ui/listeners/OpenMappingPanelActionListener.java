package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.OpenViewEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class OpenMappingPanelActionListener implements ActionListener {

    @Inject
    State state;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state.isConnected()) {
            SwingUtilities.invokeLater( () -> {
                fireEvent(new OpenViewEvent(null, OpenViewEvent.TYPE_MAPPING));
            });
        }

    }
}

