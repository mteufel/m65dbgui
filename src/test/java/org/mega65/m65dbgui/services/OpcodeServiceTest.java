package org.mega65.m65dbgui.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mega65.m65dbgui.domain.Opcode;
import org.mega65.m65dbgui.services.OpcodeService;
import org.mega65.m65dbgui.services.RegistersService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mega65.m65dbgui.util.DiUtil.create;

@DisplayName("OpcodeService Tests")
public class OpcodeServiceTest {

    OpcodeService opcodeService;

    @BeforeEach
    public void beforeEach() {
        opcodeService = (OpcodeService) create(OpcodeService.class);
    }

    @Test
    @DisplayName("Contains Opcodes")
    public void testContainsOpcodes() {
        String[] instructions = { "BNE",
                                  "CMP"
                                // "INC",
                                // "JMP",
                                // "LDA",
                                // "STA"

        };
        Arrays.asList(instructions).forEach( instruction -> assertEquals(instruction, opcodeService.getOpcodeByInstruction(instruction).getInstruction()));
    }

}
