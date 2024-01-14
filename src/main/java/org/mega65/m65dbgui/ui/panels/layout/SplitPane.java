package org.mega65.m65dbgui.ui.panels.layout;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.ui.panels.layout.LeftSplitPane;
import org.mega65.m65dbgui.ui.panels.layout.RightSplitPane;

import javax.swing.JSplitPane;

public class SplitPane extends JSplitPane {

    @Inject
    public SplitPane(LeftSplitPane leftSplitPane, RightSplitPane rightPanel) {

        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setOneTouchExpandable(false);
        setDividerLocation(700);
        setLayout(null);
        setLeftComponent(leftSplitPane);
        setRightComponent(rightPanel);
        updateUI();
    }
}
