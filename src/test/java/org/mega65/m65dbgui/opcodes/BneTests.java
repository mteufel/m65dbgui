package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Disassembler Tests - BNE")
public class BneTests extends BaseOpcodeTest {

    @Test
    @DisplayName("BNE relative $D0 - Test 1.1")
    public void testBNE1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D0"));
        input.add(Byte.parseByte("2001", "01"));

        disassemble(input);
        String expectedResult = "2000 D0 01      BNE $2003";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("BNE relative $D0 - Test 1.2")
    public void testBNE12() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("200E", "D0"));
        input.add(Byte.parseByte("200F", "F7"));

        disassemble(input);
        String expectedResult = "200E D0 F7      BNE $2007";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("BNE 16-bit relative $D3 - Test 2")
    public void testBNE2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D3"));
        input.add(Byte.parseByte("2001", "FC"));
        input.add(Byte.parseByte("2002", "0F"));

        disassemble(input);
        String expectedResult = "2000 D3 FC 0F   BNE $3000";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

}
