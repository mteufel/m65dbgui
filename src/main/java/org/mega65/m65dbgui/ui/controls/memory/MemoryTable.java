package org.mega65.m65dbgui.ui.controls.memory;

import jakarta.annotation.PostConstruct;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.domain.TimerTaskHolder;
import org.mega65.m65dbgui.events.OpenMemoryViewerEvent;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.operations.Operation;
import org.mega65.m65dbgui.timers.MemoryTimerTask;
import org.mega65.m65dbgui.ui.controls.memory.editor.ByteCellEditor;
import org.mega65.m65dbgui.ui.controls.memory.renderers.AddressRenderer;
import org.mega65.m65dbgui.ui.controls.memory.renderers.ByteRenderer;
import org.mega65.m65dbgui.ui.controls.memory.renderers.PetsciiRenderer;
import org.mega65.m65dbgui.ui.models.MemoryTableModel;
import org.mega65.m65dbgui.util.Receiver;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.util.UUID;

import static org.mega65.m65dbgui.util.DiUtil.create;


public class MemoryTable extends JTable implements Receiver {

    Logger logger = Util.getLogger(MemoryTable.class);
    MemoryTableModel model;
    MemoryTimerTask memoryTimerTask = null;
    State state;
    UUID id;


    String title;
    int refreshMode;
    long offset;
    long adrStart;
    long adrEnd;
    long adr;

    @Inject
    public MemoryTable(State state, MemoryTableModel model, PetsciiRenderer petsciiRenderer, ByteRenderer byteRenderer, AddressRenderer addressRenderer, ByteCellEditor byteCellEditor) {

        this.state = state;
        this.model = model;
        this.id = UUID.randomUUID();

        setDefaultRenderer(Long.class, addressRenderer);
        setDefaultRenderer(Byte.class, byteRenderer);
        setDefaultRenderer(String.class, petsciiRenderer);  // only the last column of the table is of type String


        setDefaultEditor(Byte.class, byteCellEditor);
        setRowSelectionAllowed(true);
        setTableHeader(null);

        InputMap im = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
        im.put(enter, im.get(f2));


    }

    @PostConstruct
    public void postConstruct() {
        model.clean();
        setModel(model);
        UiUtil.setColumnWidths(this, 90,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,26,30,100);
        state.addReceiver(this);
    }

    public void activate(String title, int refreshMode, long offset, long adrStart, long adrEnd, long adr) {

        this.title = title;
        this.refreshMode = refreshMode;
        this.offset = offset;
        this.adrStart = adrStart;
        this.adrEnd = adrEnd;
        this.adr = adr;

        if (refreshMode != OpenMemoryViewerEvent.MODE_REFRESH_MANUAL) {

            memoryTimerTask = (MemoryTimerTask) create(MemoryTimerTask.class);
            memoryTimerTask.initialize(title, offset, adrStart,adrEnd, adr, this.id);

            switch (refreshMode) {
                case OpenMemoryViewerEvent.MODE_REFRESH_FAST:
                    state.addTimerTask(new TimerTaskHolder(title, memoryTimerTask, M65Memory.DELAY_FAST, M65Memory.PERIOD_FAST));
                    break;
                case OpenMemoryViewerEvent.MODE_REFRESH_NORMAL:
                    state.addTimerTask(new TimerTaskHolder(title, memoryTimerTask, M65Memory.DELAY_NORMAL, M65Memory.PERIOD_NORMAL));
                    break;
            }
            state.startTimerTask(title);
        }

    }

    @Override
    public String toString() {
        return "title=" + this.title + " adr=" + this.adr + " " + super.toString();
    }


    @Override
    public void update(Operation operation) {

        if (operation instanceof M65Memory m65m)  {

            if (m65m.getId() != this.id)
                return;
            if (m65m.isEmpty())
                return;


            model.clean();
            m65m.getBytes().forEach( by -> model.addByte(by) );
            SwingUtilities.invokeLater( () -> {
                setModel(model);
                updateUI();
            });

        }


    }

    public MemoryTimerTask getMemoryTimerTask() {
        return memoryTimerTask;
    }

    public int getRefreshMode() {
        return refreshMode;
    }

    public long getAdr() {
        return adr;
    }

    public long getOffset() {
        return offset;
    }

    public UUID getId() {
        return id;
    }

    public void setAdr(long adr) {
        this.adr = adr;
    }

    public long getAdrEnd() {
        return adrEnd;
    }

    public long getAdrStart() {
        return adrStart;
    }

}
