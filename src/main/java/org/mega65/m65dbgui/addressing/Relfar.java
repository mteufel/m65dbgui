package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

public class Relfar extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        String addr = Util.toHex(disassembly.operand2(), 2) + Util.toHex(disassembly.operand1(), 2);
        Long relfar = disassembly.adr() + disassembly.opcode().getSize() + 1 + Util.fromHex(addr);

        if (numberFormat.equals("$")) {
            return "BNE $" + Util.toHex(relfar, 4);
        }

        if (numberFormat.equals("#")) {
            return "BNE #" + relfar;
        }

        return "???";

    }
}
