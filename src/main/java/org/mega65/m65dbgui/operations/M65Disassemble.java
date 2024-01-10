package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;

public class M65Disassemble implements Operation {

    private String command = "g7775000";
    private String result;
    Logger logger = Util.getLogger(M65Disassemble.class);

    public String getCommand() {
        return command;
    }

    public void operate(BufferedReader reader)  {
        logger.info("running");
        try {
            result = reader.readLine();
            result = reader.readLine();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage() );
            result = null;
        }

        logger.info("result=" + result);
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
}