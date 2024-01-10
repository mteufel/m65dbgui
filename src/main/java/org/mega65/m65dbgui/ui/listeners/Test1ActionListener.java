package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.services.MemoryService;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Test1ActionListener implements ActionListener {

    Logger logger = Util.getLogger(Test1ActionListener.class);

    @Inject MemoryService memoryService;
    @Inject State state;

    @Override
    public void actionPerformed(ActionEvent e) {
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
