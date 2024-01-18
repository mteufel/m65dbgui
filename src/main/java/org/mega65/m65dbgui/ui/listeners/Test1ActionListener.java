package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.domain.Register;
import org.mega65.m65dbgui.services.Disassembler;
import org.mega65.m65dbgui.services.MemoryService;
import org.mega65.m65dbgui.services.RegistersService;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Test1ActionListener implements ActionListener {

    Logger logger = Util.getLogger(Test1ActionListener.class);

    @Inject MemoryService memoryService;
    @Inject RegistersService registersService;
    @Inject Disassembler dis;
    @Inject State state;

    @Override
    public void actionPerformed(ActionEvent e) {

        memoryService.poke( dis.createProgram2());


        /*
        if ( (memoryService.peek("777D018") & 32) == 0  ) {
           // ASSUME WE HAVE A C65
            logger.info("assume we have a C65");
            Long v1 = memoryService.peek("777D050");
            Long v2 = memoryService.peek("777D050");
            Long v3 = memoryService.peek("777D050");
            logger.info ("v1 v2 v3 = " + v1 + " " + v2 + " " + v3);
            if (v1!=v2 || v1!=v3 || v2!=v3) {
                logger.info("VIC-IV");

            }

        } else {
            logger.info("can be a C64 or a MEGA65");
            memoryService.poke(Byte.parseByte("777D000", 1));
            memoryService.poke(Byte.parseByte("777D02F", 71));
            memoryService.poke(Byte.parseByte("777D02F", 83));

            memoryService.poke(Util.fromHex("777D000") + 256, 0);
            if (memoryService.peek("777D000")==1) {
                logger.info("VIC-IV");
            }

            memoryService.poke(Byte.parseByte("777D000", 1));
            memoryService.poke(Byte.parseByte("777D02F", 165));
            memoryService.poke(Byte.parseByte("777D02F", 150));

            memoryService.poke(Util.fromHex("777D000") + 256, 0);
            if (memoryService.peek("777D000")==1) {
                logger.info("VIC-III");
            }
            logger.info("VIC-II");
        }
*/

        //logger.info("stepping one cycle");
        //fireEvent(M65Trace.step());

        /*
        List<Byte> bytes = new ArrayList<>();
        bytes.add(Byte.parseByte("0001600", "EE"));
        bytes.add(Byte.parseByte("0001601", "20"));
        bytes.add(Byte.parseByte("0001602", "D0"));
        bytes.add(Byte.parseByte("0001603", "4C"));
        bytes.add(Byte.parseByte("0001604", "00"));
        bytes.add(Byte.parseByte("0001605", "16"));

        memoryService.poke(bytes);
        memoryService.go(Util.fromHex("0001600"));
        */
/*
        List<Byte> result = memoryService.getMapping();
        logger.info(result.toString());
*/

    }
}
