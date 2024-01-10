package org.mega65.m65dbgui.timers;

import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.util.TimerTask;
import java.util.UUID;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class MemoryTimerTask extends TimerTask {

    long adrStart;
    long adrEnd;
    long adr;
    long offset;
    String title;
    UUID id;
    Logger logger = Util.getLogger(MemoryTimerTask.class);

    @Override
    public void run() {
        M65Memory m65Memory = new M65Memory();
        m65Memory.setAdr(offset + adr);
        m65Memory.setId(this.id);
        fireEvent(m65Memory);
    }

    @Override
    public String toString() {
        return super.toString() + " Adr=" + Util.toHex(adr);
    }

    public void initialize(String title, long offset, long adrStart, long adrEnd, long adr, UUID id) {
        this.title = title;
        this.offset = offset;
        this.adrStart = adrStart;
        this.adrEnd = adrEnd;
        this.adr = adr;
        this.id = id;
    }

    public void setAdr(long adr) {
        this.adr = adr;
    }

    public long getAdr() {
        return adr;
    }

    public long getAdrStart() {
        return adrStart;
    }

    public long getAdrEnd() {
        return adrEnd;
    }

    public long getOffset() {
        return offset;
    }

    public String getTitle() {
        return title;
    }
}
