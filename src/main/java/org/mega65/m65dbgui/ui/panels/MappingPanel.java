package org.mega65.m65dbgui.ui.panels;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.services.MemoryService;
import org.mega65.m65dbgui.ui.models.MappingPanelTableModel;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;


@Singleton
public class MappingPanel extends JPanel {

    MemoryService memoryService;
    MappingPanelTableModel mappingTableModel;
    JTable mappingTable;
    Logger logger = Util.getLogger(MappingPanel.class);

    @Inject
    public MappingPanel(MemoryService memoryService, MappingPanelTableModel mappingTableModel) {

        this.memoryService = memoryService;
        this.mappingTableModel = mappingTableModel;

        mappingTable = new JTable();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mappingTable);
        add(scrollPane);
    }

    @PostConstruct
    public void postConstruct() {
        mappingTableModel.clean();
        mappingTable.setModel(mappingTableModel);
        UiUtil.setColumnWidths(mappingTable,85,120,85,85,1000);
        mappingTableModel.clean();
    }


    public void onGenericEvent(@Observes GenericEvent event) {
       if (event.getEvent().equals(GenericEvent.GENERIC_EVENT_MAPPING_HAS_BEEN_REFRESHED)) {
           SwingUtilities.invokeLater( () -> {
               mappingTableModel.clean();
               mappingTableModel.setData(memoryService.getMapping());
               mappingTable.setModel(mappingTableModel);
               mappingTable.updateUI();
           });
       }
    }

}
