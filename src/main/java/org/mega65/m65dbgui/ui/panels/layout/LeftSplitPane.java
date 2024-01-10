package org.mega65.m65dbgui.ui.panels.layout;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.ui.panels.RegistersPanel;
import org.mega65.m65dbgui.ui.panels.MainPanel;

import javax.swing.*;

public class LeftSplitPane extends JSplitPane {

    @Inject
    public LeftSplitPane(RegistersPanel leftPanel, MainPanel mainPanel) {

        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setOneTouchExpandable(false);
        setDividerLocation(90);
        setLayout(null);
        setLeftComponent(leftPanel);
        setRightComponent(mainPanel);
        updateUI();

    }

}
