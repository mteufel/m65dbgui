package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.domain.Byte;
import org.mega65.m65dbgui.util.Util;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class M65Memory implements Operation {

    public static final int DELAY_NORMAL = 1000;
    public static final int PERIOD_NORMAL = 1000;
    public static final int DELAY_FAST =  250;
    public static final int PERIOD_FAST = 250;

    long adr = 0;
    String command = "M";
    List<String> memory;
    UUID id;

    public String getCommand() {
        return command;
    }

    public long getAdr() {
        return adr;
    }

    public void setAdr(long adr) {
        this.adr = adr;
        this.command = "M" + Util.toHex(adr).toUpperCase();
    }

    public void operate(BufferedReader reader)  {
        try {
            memory = new ArrayList<>();
            for (int i = 0; i < 17; i++) {
                memory.add(reader.readLine());
            }
            memory.remove(0); // the first line holds the command itself, so we'll remove that
        } catch (Exception e) {
            memory = null;
        }

    }


    public List<Byte> getBytes() {
        if (memory.isEmpty())
            return null;
        List<Byte> bytes = new ArrayList<>();
        int str = 9;
        long addr = Long.parseLong(memory.get(0).substring(1,8),16);
        for ( String m : this.memory) {
            for (int i = 0; i <= 15 ; i++) {
                bytes.add(Byte.parseByte(addr, m.substring(str,str+2)));
                addr++;
                str = str + 2;
            }
            str=9;
        }
        return bytes;
    }

    @Override
    public String toString() {
        String result = "";
        if (this.memory==null) {
            return "empty";
        }
        for ( String m : this.memory) {
            result = result + m + "\n";

        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        if (memory == null) {
            return true;
        }
        return false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

