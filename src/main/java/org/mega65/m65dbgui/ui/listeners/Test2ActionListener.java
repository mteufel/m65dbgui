package org.mega65.m65dbgui.ui.listeners;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mega65.m65dbgui.State;

public class Test2ActionListener implements ActionListener {

    Logger logger = Util.getLogger(Test2ActionListener.class);
    @Inject State state;

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Load Memory");
        //fireEvent(M65Trace.traceOff());
        ////fireEvent(new M65Breakpoint("0"));
        List<String> addr = new ArrayList<>();
        int i = 0;
        addr.add(Util.toHex(0));
        while (i < 65535) {
            i++;
            if (i % 256 == 0) {
                addr.add(Util.toHex(i));
            }
        }




        Instant start = Instant.now();
        // ohne Threads
        //addr.forEach( a -> {
        //    getMem(a);
        //});



        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        logger.info("time: " + timeElapsed);
    }



}
