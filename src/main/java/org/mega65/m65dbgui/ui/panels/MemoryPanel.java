package org.mega65.m65dbgui.ui.panels;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.events.MemoryNavigationEvent;
import org.mega65.m65dbgui.events.MemorySearchEvent;
import org.mega65.m65dbgui.events.OpenMemoryViewerEvent;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.timers.MemoryTimerTask;
import org.mega65.m65dbgui.ui.MainFrame;
import org.mega65.m65dbgui.ui.toolbars.MemoryToolbar;
import org.mega65.m65dbgui.ui.controls.memory.MemoryTable;
import org.mega65.m65dbgui.ui.panels.layout.MemoryTabbedPane;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;


@Singleton
public class MemoryPanel extends JPanel {

    MemoryTabbedPane memoryTabbedPane;
    State state;
    Logger logger = Util.getLogger(MemoryPanel.class);

    @Inject
    public MemoryPanel(MemoryTabbedPane memoryTabbedPane, MemoryToolbar memoryToolbar, State state) {
        this.memoryTabbedPane = memoryTabbedPane;
        this.state = state;
        this.memoryTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        Border border = BorderFactory.createTitledBorder("Memory Viewer");
        setBorder(border);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(memoryToolbar);
        add(memoryTabbedPane);

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                memoryToolbar.enableRefresh(false);
                if (getCurrentMemoryTimerTask() == null) {
                    memoryToolbar.enableRefresh(true);
                    fireEvent(new GenericEvent(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH));
                }
            }
        };
        memoryTabbedPane.addChangeListener(changeListener);
        memoryToolbar.enableRefresh(false);
        memoryToolbar.enableMapping(false);

    }

    public void onNewMemoryViewerEvent(@Observes OpenMemoryViewerEvent event) {

        logger.info("onNewMemoryViewerEvent");
        MemoryTable memoryTable = (MemoryTable) DiUtil.create(MemoryTable.class);
        memoryTable.activate(event.getTitle(), event.getRefreshMode(), event.getOffset(), event.getAdrStart(), event.getAdrEnd(), event.getAdr());

        if (event.isCloseable()) {
            UiUtil.createClosableTab(event.getTitle(), new JScrollPane(memoryTable), memoryTabbedPane, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    state.cancelTimerTask(event.getTitle());

                }
            });
        } else {
            memoryTabbedPane.addTab(event.getTitle(), new JScrollPane(memoryTable));
        }


        int index = memoryTabbedPane.getTabCount() - 1;
        String toolTip = event.getTitle() + " (" + Util.toHexNice(event.getOffset() + event.getAdrStart()) + "-" + Util.toHexNice(event.getOffset() + event.getAdrEnd()) + ")";
        memoryTabbedPane.setToolTipTextAt(index, toolTip);

    }

    public void onGenericEvent(@Observes GenericEvent event) {

        if (event.getEvent().equals(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH)) {
            M65Memory m65Memory = new M65Memory();
            MemoryTable memoryTable = getCurrentMemoryTable();
            m65Memory.setAdr(memoryTable.getOffset() + memoryTable.getAdr());
            m65Memory.setId(memoryTable.getId());
            fireEvent(m65Memory);
        }

    }


    private MemoryTable getCurrentMemoryTable() {
        JScrollPane pane = (JScrollPane) memoryTabbedPane.getSelectedComponent();
        return (MemoryTable)  pane.getViewport().getView();
    }

    private MemoryTimerTask getCurrentMemoryTimerTask() {
        MemoryTable memoryTable = getCurrentMemoryTable();
        return memoryTable.getMemoryTimerTask();
    }

    public void onNavigation(@Observes MemoryNavigationEvent event) {

        // ----------------------------------------------
        // Memory View refreshed by a Timer
        // ----------------------------------------------
        if (getCurrentMemoryTable().getRefreshMode() != OpenMemoryViewerEvent.MODE_REFRESH_MANUAL) {
            MemoryTimerTask memoryTimerTask = getCurrentMemoryTimerTask();
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_NEXT)) {
                memoryTimerTask.setAdr(memoryTimerTask.getAdr() + event.getIncrement());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_PREVIOUS)) {
                memoryTimerTask.setAdr(memoryTimerTask.getAdr() - event.getIncrement());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_START)) {
                memoryTimerTask.setAdr(memoryTimerTask.getAdrStart());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_END)) {
                memoryTimerTask.setAdr(memoryTimerTask.getAdrEnd() - 0x100);
            }
        }

        // ----------------------------------------------
        // Memory View refreshed manually
        // ----------------------------------------------
        if (getCurrentMemoryTable().getRefreshMode() == OpenMemoryViewerEvent.MODE_REFRESH_MANUAL) {
            MemoryTable memoryTable = getCurrentMemoryTable();
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_NEXT)) {
                memoryTable.setAdr(memoryTable.getAdr() + event.getIncrement());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_PREVIOUS)) {
                memoryTable.setAdr(memoryTable.getAdr() - event.getIncrement());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_START)) {
                memoryTable.setAdr(memoryTable.getAdrStart());
            }
            if (event.getTo().equals(MemoryNavigationEvent.NAVIGATION_END)) {
                memoryTable.setAdr(memoryTable.getAdrEnd() - 0x100);
            }
            fireEvent(new GenericEvent(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH));
        }



    }

    public void onSearch(@Observes MemorySearchEvent event) {

        if (Util.isHex(event.getSearch())) {
            long search = Long.parseLong(event.getSearch(), 16);
            MemoryTable memoryTable = getCurrentMemoryTable();
            String errText = "";

            if (getCurrentMemoryTable().getRefreshMode() != OpenMemoryViewerEvent.MODE_REFRESH_MANUAL) {
                MemoryTimerTask memoryTimerTask = getCurrentMemoryTimerTask();
                if (search >= memoryTimerTask.getAdrStart()  && search <= memoryTimerTask.getAdrEnd()) {
                    memoryTimerTask.setAdr(search);
                } else {
                    errText = "Not in the correct range (" + Util.toHex(memoryTimerTask.getAdr()) +"-" + Util.toHex(memoryTimerTask.getAdrEnd()) + ")";
                }
            } else {
                if (search >= memoryTable.getAdrStart()  && search <= memoryTable.getAdrEnd()) {
                    memoryTable.setAdr(search);
                    fireEvent(new GenericEvent(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH));
                } else {
                    errText = "Not in the correct range (" + Util.toHex(memoryTable.getAdr()) +"-" + Util.toHex(memoryTable.getAdrEnd()) + ")";
                }
            }
            if (!errText.equals("")) {
                MainFrame mainFrame = (MainFrame) SwingUtilities.windowForComponent(this);
                JOptionPane.showMessageDialog(mainFrame, errText, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            MainFrame mainFrame = (MainFrame) SwingUtilities.windowForComponent(this);
            JOptionPane.showMessageDialog(mainFrame, "Wrong input", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
