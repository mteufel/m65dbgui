package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

public class Bpr8 extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        String a = "";
        String b = "";

        if (numberFormat.equals("$")) {
            a = "$" + Util.toHex(disassembly.operand1());
            b = "$" + Util.toHex(disassembly.adr() + disassembly.opcode().getSize() + disassembly.operand2());
        }

        if (numberFormat.equals("#")) {
            a = "#" + disassembly.operand1();
            b = "#" + disassembly.operand2();
        }

        return a  + "," + b;

    }
}
