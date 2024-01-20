package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

public class BpiX extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        String result = "";

        if (numberFormat.equals("$")) {
            result = "$" + Util.toHex(disassembly.operand1());
        }

        if (numberFormat.equals("#")) {
            result = "#" + disassembly.operand1();
        }

        return "(" + result + ",X)";

    }
}
