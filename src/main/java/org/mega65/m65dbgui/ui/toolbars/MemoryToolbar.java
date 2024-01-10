package org.mega65.m65dbgui.ui.toolbars;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.events.GenericEvent;
import org.mega65.m65dbgui.events.MemoryNavigationEvent;
import org.mega65.m65dbgui.services.IconService;
import org.mega65.m65dbgui.ui.listeners.AddMemoryTabListener;
import org.mega65.m65dbgui.ui.listeners.FireGenericEventActionListener;
import org.mega65.m65dbgui.ui.listeners.MemoryNavigationActionListener;
import org.mega65.m65dbgui.ui.listeners.MemorySearchActionListener;

import javax.swing.*;
import java.awt.*;

public class MemoryToolbar extends JToolBar {

    JButton buttonAdd;
    JButton buttonRefresh;
    JButton buttonSearch;
    JButton buttonFirst;
    JButton buttonPrevious;
    JButton buttonNext;
    JButton buttonLast;
    JButton buttonMappingOnOff;

    @Inject
    public MemoryToolbar(IconService iconService,
                         MemorySearchActionListener memorySearchActionListener,
                         MemoryNavigationActionListener firstPageActionListener,
                         MemoryNavigationActionListener nextPageActionListener,
                         MemoryNavigationActionListener previousPageActionListener,
                         MemoryNavigationActionListener lastPageActionListener,
                         AddMemoryTabListener addMemoryTabListener,
                         FireGenericEventActionListener fireGenericEventActionListener) {

        buttonAdd = new JButton(iconService.geSmalltToolbarIcon("add.svg"));
        buttonAdd.addActionListener(addMemoryTabListener);
        buttonAdd.setToolTipText("Add another memory location");
        add(buttonAdd);

        buttonSearch = new JButton(iconService.getSearch());
        buttonSearch.addActionListener(memorySearchActionListener);
        buttonSearch.setToolTipText("Enter a memory location");
        add(buttonSearch);

        addSeparator();

        buttonFirst = new JButton(iconService.getFirst());
        firstPageActionListener.setTo(MemoryNavigationEvent.NAVIGATION_START);
        buttonFirst.addActionListener(firstPageActionListener);
        buttonFirst.setToolTipText("Navigate to begin of memory");
        add(buttonFirst);

        buttonPrevious = new JButton(iconService.getPrevious());
        previousPageActionListener.setTo(MemoryNavigationEvent.NAVIGATION_PREVIOUS);
        buttonPrevious.addActionListener(previousPageActionListener);
        buttonPrevious.setToolTipText("Go to previous page, hold shift for larger decrement");
        add(buttonPrevious);

        buttonNext = new JButton(iconService.getNext());
        nextPageActionListener.setTo(MemoryNavigationEvent.NAVIGATION_NEXT);
        buttonNext.addActionListener(nextPageActionListener);
        buttonNext.setToolTipText("Go to next page, hold shift for larger increment");
        add(buttonNext);

        buttonLast = new JButton(iconService.getLast());
        lastPageActionListener.setTo(MemoryNavigationEvent.NAVIGATION_END);
        buttonLast.addActionListener(lastPageActionListener);
        buttonLast.setToolTipText("Go to end of Memory");
        add(buttonLast);

        addSeparator();

        buttonMappingOnOff = new JButton(iconService.geSmalltToolbarIcon("mapping.svg"));
        buttonMappingOnOff.setToolTipText("Show mapping informations in memory viewer");
        add(buttonMappingOnOff);

        addSeparator();

        buttonRefresh = new JButton(iconService.geSmalltToolbarIcon("refresh.svg"));
        fireGenericEventActionListener.setEvent(GenericEvent.GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH);
        buttonRefresh.addActionListener(fireGenericEventActionListener);
        buttonRefresh.setToolTipText("Refresh data");
        add(buttonRefresh);

        add(Box.createHorizontalGlue());

        //JButton buttonMore = new JButton(iconService.getMore());
        //buttonConnect.addActionListener(connectDisconnectActionListener);
        //buttonMore.setToolTipText("Memory configuration");
        //add(buttonMore);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

    }

    public void enableRefresh(boolean enable) {
        this.buttonRefresh.setEnabled(enable);
    }
    public void enableMapping(boolean enable) {
        this.buttonMappingOnOff.setEnabled(enable);
    }

}
