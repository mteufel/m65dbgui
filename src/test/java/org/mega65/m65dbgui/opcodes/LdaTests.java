package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Disassembler Tests - LDA")
public class LdaTests extends BaseOpcodeTest {

    @Test
    @DisplayName("LDA (indirect,X) $A1")
    public void test1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "A1"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 A1 12      LDA ($12,X)";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("LDA base-page $A5")
    public void test2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "A5"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 A5 12      LDA $12";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("LDA immediate 8bit $A9")
    public void test3() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "A9"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 A9 12      LDA #$12";

        assertEquals(expectedResult,disassembler.getLine(0));
    }


}
