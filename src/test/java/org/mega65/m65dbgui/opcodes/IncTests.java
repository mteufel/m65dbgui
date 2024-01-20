package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Disassembler Tests - INC")
public class IncTests extends BaseOpcodeTest {

    @Test
    @DisplayName("INC accumulator $1A")
    public void testINC1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "1A"));

        disassemble(input);
        String expectedResult = "2000 1A         INC";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("INC base-page $E6")
    public void testINC2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "E6"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 E6 12      INC $12";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("INC absolute $EE")
    public void testINC3() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "EE"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 EE 34 12   INC $1234";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("INC base-page,X $F6")
    public void testINC4() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "F6"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 F6 12      INC $12,X";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("INC absolute,X $FE")
    public void testINC5() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "FE"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 FE 34 12   INC $1234,X";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

}
