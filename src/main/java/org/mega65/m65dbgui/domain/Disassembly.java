package org.mega65.m65dbgui.domain;

public record Disassembly(Long adr, Opcode opcode, Long code, Long operand1, Long operand2) {
    public static Disassembly create(Long adr, Opcode opcode) {
        return new Disassembly(adr, opcode, Long.parseLong(opcode.getOp(), 16), null, null);
    }
    public static Disassembly create(Long adr, Opcode opcode, Long operand1) {
        return new Disassembly(adr, opcode, Long.parseLong(opcode.getOp(), 16), operand1, null);
    }
    public static Disassembly create(Long adr, Opcode opcode, Long operand1, Long operand2) {
        return new Disassembly(adr, opcode, Long.parseLong(opcode.getOp(), 16), operand1, operand2);
    }

}
