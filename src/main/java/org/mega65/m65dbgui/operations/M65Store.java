package org.mega65.m65dbgui.operations;

import com.kitfox.svg.A;
import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class M65Store implements Operation {


    private long adr = -1;
    private long value = -1;
    private List<Byte> bytes = new ArrayList<>();
    Logger logger = Util.getLogger(M65Store.class);


    public void setAdr(long adr) {
        this.adr = adr;
    }
    public void setValue(long value) {
        this.value = value;
        this.bytes = new ArrayList<>();
    }
    public void setBytes(List<Byte> bytes) {
        this.value = -1;
        this.bytes = bytes;
    }
    public void setByte(Byte by) {
        this.value = -1;
        this.bytes = new ArrayList<>();
        this.bytes.add(by);
    }


    public String getCommand() {
        String adr = "";
        String command = "";

        if (value != -1) {
            adr =  Util.toHex(this.adr, 7).toUpperCase();
            command =  "s" + adr + " " +  Util.toHex(value);
        }

        if (!bytes.isEmpty()) {
            adr = Util.toHex(bytes.get(0).getAddr(), 7).toUpperCase();
            command =  "s" + adr + " ";
            for (Byte by : bytes) {
                command = command + " " + Util.toHex(by.getValue()).toUpperCase();

            }
        }

        logger.info("command=" + command);
        return command;
    }

    @Override
    public void operate(BufferedReader reader) throws IOException {
        try {
            logger.info(reader.readLine());
            this.value = -1;
            this.bytes = new ArrayList<>();

        } catch (Exception e) {
            logger.error("Error: " + e.getMessage() );
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
