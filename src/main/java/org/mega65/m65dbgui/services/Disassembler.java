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
    List<Disassembly> disassembies;
    List<Byte> bytes;

    public List<Byte> createProgram() {

        // ADR  OP O1 O2   INS             CYC
        // --------------------------------------
        // 5000 EE 20 D0   INC $D020       3
        // 5003 A2 AA      LDX #$aa        2
        // 5005 8E AA 51   STX $51AA       3
        // 5008 8A         TXA             1
        // 5009 4C 00 50   JMP $5000       3

        List<Byte> program = new ArrayList<Byte>();
        program.add(new Byte(20480, 238));
        program.add(new Byte(20481, 32));
        program.add(new Byte(20482, 208));
        program.add(new Byte(20483, 162));
        program.add(new Byte(20484, 170));
        program.add(new Byte(20485, 142));
        program.add(new Byte(20486, 170));
        program.add(new Byte(20487, 81));
        program.add(new Byte(20488, 138));
        program.add(new Byte(20489, 76));
        program.add(new Byte(20490, 0));
        program.add(new Byte(20491, 80));
        return program;

    }

    public void disassemble(List<Byte> bytes) {

        int idx = 0;
        Disassembly dis = null;
        Long adr, operand1, operand2;

        disassembies = new ArrayList<>();

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
            disassembies.add(dis);
        }

    }

    public List<Disassembly> getDisassembly() {
        return disassembies;
    }
}

