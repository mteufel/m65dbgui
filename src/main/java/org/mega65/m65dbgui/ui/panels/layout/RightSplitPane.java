package org.mega65.m65dbgui.ui.panels.layout;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.ui.panels.MemoryPanel;
import org.mega65.m65dbgui.ui.panels.WatchesPanel;

import javax.swing.*;


public class RightSplitPane extends JSplitPane {

    @Inject
    public RightSplitPane(WatchesPanel watchesPanel, MemoryPanel memoryPanel) {

        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setOneTouchExpandable(false);
        setDividerLocation(400);
        setLayout(null);
        setLeftComponent(watchesPanel);
        setRightComponent(memoryPanel);
        updateUI();

    }

}
