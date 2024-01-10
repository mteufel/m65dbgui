package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.operations.M65Registers;
import org.mega65.m65dbgui.services.IconService;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectDisconnectActionListener implements ActionListener {


    IconService iconService;
    State state;

    @Inject
    public ConnectDisconnectActionListener(IconService iconService, State state) {
        this.iconService = iconService;
        this.state = state;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.state.connect(!this.state.isConnected());

        JButton button = (JButton) e.getSource();
        if (state.isConnected()) {
            button.setIcon(iconService.getIconConneced());
        } else {
            button.setIcon(iconService.getIconDisonneced());
        }
    }
}
