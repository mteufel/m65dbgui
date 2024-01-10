package org.mega65.m65dbgui.ui.panels;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.events.OpenViewEvent;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.services.IconService;
import org.mega65.m65dbgui.ui.controls.disassembly.DisassemblyTable;
import org.mega65.m65dbgui.ui.panels.layout.MainTabbedPane;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;

@Singleton
public class MainPanel extends JPanel {

    MainTabbedPane mainTabbedPane;
    State state;
    Logger logger = Util.getLogger(MainPanel.class);
    IconService iconService;

    @Inject
    public MainPanel(MainTabbedPane mainTabbedPane, State state, IconService iconService) {
        this.mainTabbedPane = mainTabbedPane;
        this.mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.state = state;
        this.iconService = iconService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(mainTabbedPane);
    }


    public void onNewViewEvent(@Observes OpenViewEvent event) {
        logger.info("onNewViewEvent");
        String title = "???";
        if (event.getType().equals(OpenViewEvent.TYPE_DISASSEMBLY)) {
            title = "Disassembly " + event.getData();
            DisassemblyTable disassemblyTable = (DisassemblyTable) DiUtil.create(DisassemblyTable.class);
            M65Memory m65Memory = new M65Memory();
            m65Memory.setId(disassemblyTable.getId());
            m65Memory.setAdr(0x2015);
            UiUtil.createClosableTab(title, new JScrollPane(disassemblyTable), mainTabbedPane, null);
            state.executeOperation(m65Memory);
         }
        if (event.getType().equals(OpenViewEvent.TYPE_MAPPING) && !isMappingPaneOpen()) {
            title = "Mapping";
            MappingPanel mappingPanel = (MappingPanel) DiUtil.create(MappingPanel.class);
            UiUtil.createClosableTab(title, mappingPanel, mainTabbedPane, null);
            DiUtil.fireEvent(new GenericEvent(GenericEvent.GENERIC_EVENT_MAPPING_HAS_BEEN_REFRESHED));
        }

    }

    public boolean isMappingPaneOpen() {
        int totalTabs = mainTabbedPane.getTabCount();
        for(int i = 0; i < totalTabs; i++)
        {
            if (mainTabbedPane.getTabComponentAt(i) instanceof MappingPanel)  {
                return true;
            }
        }
        return false;
    }

}