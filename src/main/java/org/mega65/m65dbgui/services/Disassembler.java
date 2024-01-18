package org.mega65.m65dbgui.services;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.domain.Disassembly;
import org.mega65.m65dbgui.domain.Opcode;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Disassembler {

    Logger logger = Util.getLogger(Disassembler.class);
    @Inject OpcodeService opcodeService;
    List<Disassembly> disassemblies;
    List<Byte> bytes;

    public List<Byte> createProgram() {

        // ADR  OP O1 O2   INS             CYC
        // --------------------------------------
        // 1000 EE 20 D0   INC $D020       3
        // 1003 A2 AA      LDX #$aa        2
        // 1005 8E AA 51   STX $51AA       3
        // 1008 8A         TXA             1
        // 1009 4C 00 50   JMP $5000       3

        List<Byte> program = new ArrayList<Byte>();
        program.add(Byte.parseByte("1000", 238));
        program.add(Byte.parseByte("1001", 32));
        program.add(Byte.parseByte("1002", 208));
        program.add(Byte.parseByte("1003", 162));
        program.add(Byte.parseByte("1004", 170));
        program.add(Byte.parseByte("1005", 142));
        program.add(Byte.parseByte("1006", 170));
        program.add(Byte.parseByte("1007", 81));
        program.add(Byte.parseByte("1008", 138));
        program.add(Byte.parseByte("1009", 76));
        program.add(Byte.parseByte("100A", 0));
        program.add(Byte.parseByte("100B", 80));
        return program;

    }


    public List<Byte> createProgram2() {
        List<Byte> program = new ArrayList<>();
        program.add(Byte.parseByte("1000", "A9"));      // LDA #$00
        program.add(Byte.parseByte("1001", "00"));
        program.add(Byte.parseByte("1002", "8D"));      // STA $D020
        program.add(Byte.parseByte("1003", "20"));
        program.add(Byte.parseByte("1004", "D0"));
        program.add(Byte.parseByte("1005", "1A"));      // INC
        program.add(Byte.parseByte("1006", "C9"));      // CMP #$0F
        program.add(Byte.parseByte("1007", "0F"));
        program.add(Byte.parseByte("1008", "D0"));      // BNE $1002
        program.add(Byte.parseByte("1009", "F8"));
        program.add(Byte.parseByte("100a", "4C"));      // JMP $1000
        program.add(Byte.parseByte("100b", "00"));
        program.add(Byte.parseByte("100c", "10"));
        return program;
    }


    public void disassemble(List<Byte> bytes) {

        int idx = 0;
        Disassembly dis = null;
        Long adr, operand1, operand2;

        disassemblies = new ArrayList<>();

        while (idx <= bytes.size()-1) {
            Byte by = bytes.get(idx);
            Opcode opcode = opcodeService.getOpcodeByCode(by.getValue());


            switch (opcode.getSize()) {
                case 1:
                    dis = Disassembly.create(by.getAddr(), opcode);
                    break;
                case 2:
                    adr = by.getAddr();
                    idx++;
                    operand1 = bytes.get(idx).getValue();
                    dis = Disassembly.create(adr, opcode, operand1);
                    break;
                case 3:
                    adr = by.getAddr();
                    idx++;
                    operand1 = bytes.get(idx).getValue();
                    idx++;
                    operand2 = bytes.get(idx).getValue();
                    dis = Disassembly.create(adr, opcode, operand1, operand2);
            }
            idx++;
            disassemblies.add(dis);
        }

    }

    public List<Disassembly> getDisassembly() {
        return disassemblies;
    }

    public String getHeader() {
        return "\nADR  OP O1 O2   INS             CYC\n" +
               "-----------------------------------------";
    }
    public String getLine(int index) {
        Disassembly dis = disassemblies.get(index);

        String operand1 = "  ";
        if (dis.operand1() != null) {
            operand1 =  Util.toHex(dis.operand1(), 2);
        }
        String operand2 = "  ";
        if (dis.operand2() != null) {
            operand2 =  Util.toHex(dis.operand2(), 2);
        }

        return Util.toHex(dis.adr(),4) + " " +
               Util.toHex(dis.code(), 2) + " " +
                operand1 + " " +
                operand2 + "   " +
                dis.opcode().getAdressing().getInstruction("$", dis);

    }

}

