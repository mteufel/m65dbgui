package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.BeforeEach;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.services.Disassembler;

import java.util.List;

import static org.mega65.m65dbgui.util.DiUtil.create;

public class BaseOpcodeTest {

    Disassembler disassembler;

    @BeforeEach
    public void beforeEach() {
        disassembler = (Disassembler) create(Disassembler.class);
    }

    protected void disassemble(List<Byte> input) {
        disassembler.disassemble(input);
    }
    protected void disassembleWithOutput(List<Byte> input) {
        disassembler.disassemble(input);
        System.out.println(disassembler.getHeader());
        System.out.println(disassembler.getLine(0));
        System.out.println("\n");
    }

}
