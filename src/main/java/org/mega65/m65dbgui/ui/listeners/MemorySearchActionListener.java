package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.MemorySearchEvent;
import org.mega65.m65dbgui.ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class MemorySearchActionListener implements ActionListener {

    @Inject
    State state;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state.isConnected()) {
            MainFrame mainFrame = (MainFrame) SwingUtilities.windowForComponent((JButton)e.getSource());
            String search = JOptionPane.showInputDialog(mainFrame, "Enter location:");
            fireEvent(new MemorySearchEvent(search));
        }
    }


}
