package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

public class Rel extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        Long rel;

        if (disassembly.operand1() >= 1 && disassembly.operand1() <= Util.fromHex("7F")) {
            // $01-$7F forward
            rel = (disassembly.adr() + disassembly.opcode().getSize() ) + disassembly.operand1();
        } else {
            // $80-$FD backward
            rel = (disassembly.adr() + disassembly.opcode().getSize() - 1 ) - (Util.fromHex("FF") - (disassembly.operand1()));
        }

        if (numberFormat.equals("$")) {
            return "BNE $" + Util.toHex(rel, 4);
        }

        if (numberFormat.equals("#")) {
            return "BNE #" + rel;
        }

        return "???";
    }
}
