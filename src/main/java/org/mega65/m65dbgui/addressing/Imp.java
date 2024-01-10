package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;

public class Imp extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        // In this mode there are no operands, the opcode mneomics stand for themself, so we return nothing here
        return "";

    }
}
