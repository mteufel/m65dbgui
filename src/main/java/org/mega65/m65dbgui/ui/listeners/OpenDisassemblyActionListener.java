package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.OpenViewEvent;
import org.mega65.m65dbgui.ui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class OpenDisassemblyActionListener implements ActionListener {

    @Inject
    State state;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state.isConnected()) {
            MainFrame mainFrame = (MainFrame) SwingUtilities.windowForComponent((JButton)e.getSource());

            SwingUtilities.invokeLater( () -> {
                //String search = JOptionPane.showInputDialog(mainFrame, "Enter adress range to disassemble:");
                //fireEvent(new OpenViewEvent(search, OpenViewEvent.TYPE_DISASSEMBLY));
                fireEvent(new OpenViewEvent(null, OpenViewEvent.TYPE_MAPPING));
            });


        }

    }
}