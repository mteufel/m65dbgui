package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;

public class Rel extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {
        return "REL!!!!";
    }
}
