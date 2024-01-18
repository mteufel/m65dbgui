package org.mega65.m65dbgui.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Byte;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mega65.m65dbgui.util.DiUtil.create;

@DisplayName("Disassembler Tests")
public class DisassemblerTests {

    Disassembler disassembler;

    @BeforeEach
    public void beforeEach() {
        disassembler = (Disassembler) create(Disassembler.class);
    }

    @Test
    @DisplayName("BNE relative $D0")
    public void testBNE1() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D0"));
        input.add(Byte.parseByte("2001", "01"));

        disassemble(input);
        String expectedResult = "2000 D3 FC 0F   BNE $3000";

        assertEquals(expectedResult,disassembler.getLine(0));
    }

    @Test
    @DisplayName("BNE 16-bit relative $D3")
    public void testBNE2() {

        List<Byte> input = new ArrayList<>();
        input.add(Byte.parseByte("2000", "D3"));
        input.add(Byte.parseByte("2001", "FC"));
        input.add(Byte.parseByte("2002", "0F"));

        disassemble(input);
        String expectedResult = "2000 D3 FC 0F   BNE $3000";

        assertEquals(expectedResult,disassembler.getLine(0));
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
