package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Disassembler Tests - JMP")
public class JmpTests extends BaseOpcodeTest {

    @Test
    @DisplayName("JMP absolute $4C")
    public void test1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "4C"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 4C 34 12   JMP $1234";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("JMP (indirect) $6C")
    public void test2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "6C"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 6C 34 12   JMP ($1234)";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("JMP (indirect,X) $7C")
    public void test3() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "7C"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 7C 34 12   JMP ($1234,X)";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

}
