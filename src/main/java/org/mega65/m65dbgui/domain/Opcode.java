package org.mega65.m65dbgui.domain;

import org.mega65.m65dbgui.addressing.Addressing;

public class Opcode {

    private String op;
    private String instruction;
    private int size;
    private int cycles;
    private String cyclesNotes;
    private Addressing adressing;
    private String compound;

    public Opcode(String op, String instruction, int size, int cycles, String cyclesNotes, Addressing adressing, String compound) {
        this.op = op;
        this.instruction = instruction;
        this.size = size;
        this.cycles = cycles;
        this.cyclesNotes = cyclesNotes;
        this.adressing = adressing;
        this.compound = compound;
    }

    public String getOp() {
        return op;
    }
    public String getInstruction() {
        return instruction;
    }
    public int getSize() {
        return size;
    }
    public int getCycles() {
        return cycles;
    }
    public Addressing getAdressing() {
        return adressing;
    }

}

