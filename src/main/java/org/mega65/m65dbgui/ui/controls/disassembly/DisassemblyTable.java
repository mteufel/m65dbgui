package org.mega65.m65dbgui.ui.controls.disassembly;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.operations.Operation;
import org.mega65.m65dbgui.services.Disassembler;
import org.mega65.m65dbgui.ui.models.DisassemblyTableModel;
import org.mega65.m65dbgui.util.Receiver;
import org.mega65.m65dbgui.util.UiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import javax.swing.*;

import java.util.UUID;

public class DisassemblyTable extends JTable implements Receiver  {

    Logger logger = Util.getLogger(DisassemblyTable.class);
    UUID id;
    DisassemblyTableModel model;
//    State state;
    Disassembler disassembler;

    @Inject
    public DisassemblyTable(State state, DisassemblyTableModel model, Disassembler disassembler) {
        this.id = UUID.randomUUID();
        this.model = model;
        this.disassembler = disassembler;
        setModel(model);
        UiUtil.setColumnWidths(this, 60,60,60,60,60,200,60,60);
        state.addReceiver(this);
        setRowSelectionAllowed(true);

    }





    @Override
    public void update(Operation operation) {


        if (operation instanceof M65Memory m65m) {

            if (m65m.isEmpty() || m65m.getId() == null)
                return;

            if (m65m.getId().equals(this.id)) {
                disassembler.disassemble(m65m.getBytes());
                model.setDisassembly(disassembler.getDisassembly());
                SwingUtilities.invokeLater( () -> {
                    setModel(model);
                    updateUI();
                });

            }

        }


    }

    public UUID getId() {
        return id;
    }
}
