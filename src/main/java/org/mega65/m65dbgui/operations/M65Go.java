package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;

public class M65Go implements Operation {

    private String command = "g";
    private long addr;
    private String result;
    Logger logger = Util.getLogger(M65Go.class);

    public String getCommand() {
        return command + Util.toHex(addr);
    }

    public void operate(BufferedReader reader)  {
        try {
            result = reader.readLine();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage() );
            result = null;
        }
    }

    @Override
    public boolean isEmpty() {
        if (result==null) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return result;
    }

    public void setAddr(long addr) {
        this.addr = addr;
    }
}