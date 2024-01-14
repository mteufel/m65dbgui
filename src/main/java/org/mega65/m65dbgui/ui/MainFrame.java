package org.mega65.m65dbgui.ui;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.util.SystemInfo;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.TimerTaskHolder;
import org.mega65.m65dbgui.events.OpenMemoryViewerEvent;
import org.mega65.m65dbgui.operations.M65Registers;
import org.mega65.m65dbgui.timers.RegistersTimerTask;
import org.mega65.m65dbgui.ui.toolbars.MainToolbar;
import org.mega65.m65dbgui.ui.panels.layout.SplitPane;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

import javax.swing.*;
import java.awt.*;

@Singleton
public class MainFrame extends JFrame {

    @Inject State state;
    @Inject RegistersTimerTask registersTimerTask;
    @Inject MainToolbar toolbar;
    @Inject SplitPane splitPane;


    @PostConstruct
    public void postConstruct() {

        setTitle("m65dbg-ui");
        if (SystemInfo.isMacFullWindowContentSupported) {
            setTitle(null);
        }

        setSize(1420,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // add the Toolbar
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.NORTH, toolbar);

        add(splitPane);


        // open the initial two MemoryViewer Tabs ....
        fireEvent(new OpenMemoryViewerEvent(false, "IO", 0x7770000, 0xd000, 0xdfff, OpenMemoryViewerEvent.MODE_REFRESH_FAST ));
        fireEvent(new OpenMemoryViewerEvent(false, "RAM",   0x7770000, 0x0000, 0xffff, 0x5000, OpenMemoryViewerEvent.MODE_REFRESH_FAST));
        fireEvent(new OpenMemoryViewerEvent(true, "RAM.000.0", Util.fromHex("000" + "0000"), Util.fromHex("0002"), Util.fromHex("ffff"), Util.fromHex("5000"), OpenMemoryViewerEvent.MODE_REFRESH_MANUAL));


        // activate the task for querying the registers ....
        state.addTimerTask(new TimerTaskHolder(RegistersTimerTask.REGISTERS_TIMER_TASK_ID, registersTimerTask, M65Registers.DELAY, M65Registers.PERIOD));
        state.startTimerTask(RegistersTimerTask.REGISTERS_TIMER_TASK_ID);

        // My Home ArchBox
        UiUtil.showOnScreen(1, this);  // if monitor (screen) 1 does not exist, the window will be shown on the primary monitor
        // My MacBook
        //UiUtil.showOnScreen(0, this);  // if monitor (screen) 1 does not exist, the window will be shown on the primary monitor


    }


    public void configureLookAndFeel() throws Exception {

        UIManager.setLookAndFeel(new FlatDarculaLaf());
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);
        FlatLaf.setUseNativeWindowDecorations(false);

        FlatLaf.updateUI();
    }

}

