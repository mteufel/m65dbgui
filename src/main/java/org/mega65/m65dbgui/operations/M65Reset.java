package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;

public class M65Reset implements Operation {

    private String command = "~reset";
    private String result;
    Logger logger = Util.getLogger(M65Reset.class);

    public String getCommand() {
        return command;
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

}