package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.util.Util;

public class AbsX extends Addressing {

    @Override
    public String getInstruction(String numberFormat, Disassembly disassembly) {

        return "$" +  Util.toHex(disassembly.operand2()) +  Util.toHex(disassembly.operand1()) + ",X";

    }
}
