package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Disassembler Tests - Others")
public class OtherOpcodesTest extends BaseOpcodeTest {

    @Test
    @DisplayName("BRK implied $00")
    public void testBRK() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "00"));

        disassemble(input);
        String expectedResult = "2000 00         BRK";

        assertEquals(expectedResult,disassembler.getLine(0));
    }
}
