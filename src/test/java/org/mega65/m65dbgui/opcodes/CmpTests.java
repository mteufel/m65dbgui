package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.services.Disassembler;

import java.util.List;

import static org.mega65.m65dbgui.util.DiUtil.create;

@DisplayName("Disassembler Tests")
public class CmpTests {

    Disassembler disassembler;

    @BeforeEach
    public void beforeEach() {
        disassembler = (Disassembler) create(Disassembler.class);
    }


    private void disassemble(List<Byte> input) {
        disassembler.disassemble(input);
    }
    private void disassembleWithOutput(List<Byte> input) {
        disassembler.disassemble(input);
        System.out.println(disassembler.getHeader());
        System.out.println(disassembler.getLine(0));
        System.out.println("\n");
    }

}
