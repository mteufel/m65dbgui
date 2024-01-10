package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;

public class M65Trace implements Operation {

    private String command = "t";
    private String result;
    Logger logger = Util.getLogger(M65Reset.class);

    public M65Trace(String traceCommand) {
        // 0 = Disable Tracing
        // 1 = Enable Tracing
        // space = Step one cycle if in trace mode
        this.command = "t" + traceCommand;
    }

    public String getCommand() {
        return command;
    }

    public void operate(BufferedReader reader) {
        try {
            result = reader.readLine();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            result = null;
        }
    }

    @Override
    public boolean isEmpty() {
        if (result == null) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return result;
    }

    public static M65Trace traceOn() {
        return new M65Trace("1");
    }
    public static M65Trace traceOff() {
        return new M65Trace("0");
    }
    public static M65Trace step() {
        return new M65Trace(" ");
    }
}