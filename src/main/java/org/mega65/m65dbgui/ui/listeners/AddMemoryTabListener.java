package org.mega65.m65dbgui.ui.listeners;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.ui.MainFrame;
import org.mega65.m65dbgui.ui.dialogs.MemorySelectionDialog;
import org.mega65.m65dbgui.util.DiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddMemoryTabListener implements ActionListener {

    @Inject State state;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (state.isConnected()) {

            MemorySelectionDialog memorySelectionDialog = (MemorySelectionDialog) DiUtil.create(MemorySelectionDialog.class);
            memorySelectionDialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor((Component) e.getSource()));
            memorySelectionDialog.setSize(new Dimension(800,600));
            memorySelectionDialog.pack();
            memorySelectionDialog.setVisible(true);

        }
    }


}
