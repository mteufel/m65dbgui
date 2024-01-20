package org.mega65.m65dbgui.opcodes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Disassembler Tests - CMP")
public class CmpTests extends BaseOpcodeTest {

    @Test
    @DisplayName("CMP (indirect,X) $C1")
    public void testCMP1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "C1"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 C1 12      CMP ($12,X)";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP base-page,X $D5")
    public void testCMP2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D5"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 D5 12      CMP $12,X";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP base-page $C5")
    public void testCMP3() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "C5"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 C5 12      CMP $12";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP immediate 8-bit $C9")
    public void testCMP4() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "C9"));
        input.add(Byte.parseByte("2001", "05"));

        disassemble(input);
        String expectedResult = "2000 C9 05      CMP #$05";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP absolute $CD")
    public void testCMP5() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "CD"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 CD 34 12   CMP $1234";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP (indirect),Y $D1")
    public void testCMP6() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D1"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 D1 12      CMP ($12),Y";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP (indirect),Z $D2")
    public void testCMP7() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D2"));
        input.add(Byte.parseByte("2001", "12"));

        disassemble(input);
        String expectedResult = "2000 D2 12      CMP ($12),Z";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP absolute,Y $D9")
    public void testCMP8() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D9"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 D9 34 12   CMP $1234,Y";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("CMP absolute,X $DD")
    public void testCMP9() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "DD"));
        input.add(Byte.parseByte("2001", "34"));
        input.add(Byte.parseByte("2002", "12"));

        disassemble(input);
        String expectedResult = "2000 DD 34 12   CMP $1234,X";

        assertEquals(expectedResult,disassembler.getLine(0));
    }


}

