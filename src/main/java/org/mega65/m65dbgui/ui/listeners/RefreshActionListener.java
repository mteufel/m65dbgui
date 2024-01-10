package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.operations.M65Breakpoint;
import org.mega65.m65dbgui.operations.M65Disassemble;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.operations.M65Trace;
import org.mega65.m65dbgui.services.Disassembler;
import org.mega65.m65dbgui.domain.Byte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class RefreshActionListener implements ActionListener {

    @Inject Disassembler disassembler;

    @Override
    public void actionPerformed(ActionEvent e) {
        //fireEvent(new M65Disassemble());
        //List<Byte> prg = disassembler.createProgram();
        //disassembler.disassemble(prg);
        fireEvent(new M65Breakpoint("5000"));


    }

}
