package org.mega65.m65dbgui.operations;

import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;


public class M65Registers implements  Operation {

    public static final int DELAY = 50;
    public static final int PERIOD = 50;

    Logger logger = Util.getLogger(M65Registers.class);

    private String command = "r";
    private String result1;
    private String result2;
    private String result3;
    private String result4;
    private String pc;
    private String ar;
    private String xr;
    private String yr;
    private String zr;
    private String bp;
    private String sp;
    private String mapl;
    private String maph;
    private String lastOp;
    private String unknown;
    private String sr;
    private boolean n;
    private boolean v;
    private boolean e;
    private boolean b;
    private boolean d;
    private boolean i;
    private boolean z;
    private boolean c;


    public String getCommand() {
        return command;
    }

    public void operate(BufferedReader reader)  {
        try {
            result1 = reader.readLine();
            result2 = reader.readLine();
            result3 = reader.readLine();
            result4 = reader.readLine();
            parse();

        } catch (Exception e) {
            logger.error("Error: " + e.getMessage() );
            result1 = null;
            result2 = null;
            result3 = null;
            result4 = null;
        }
    }

    private void parse() {
        pc = result4.substring(0,4);
        ar = result4.substring(5,7);
        xr = result4.substring(8,10);
        yr = result4.substring(11,13);
        zr = result4.substring(14,16);
        bp = result4.substring(17,19);
        sp = result4.substring(20,24);
        mapl = result4.substring(25,29);
        maph = result4.substring(30,34);
        lastOp = result4.substring(35,37);
        sr = result4.substring(44,46);
        unknown = result4.substring(47,49);

        n = checkFlag(50);
        v = checkFlag(51);
        e = checkFlag(52);
        b = checkFlag(53);
        d = checkFlag(54);
        i = checkFlag(55);
        z = checkFlag(56);
        c = checkFlag(57);

    }

    private boolean checkFlag(int index) {
        if (result4.substring(index,index+1).equals("-")) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "\n";
        //result = result + result1 + "\n";  -- contains just the command, so we dont need to get it back here
        result = result + result2 + "\n";
        result = result + result3 + "\n";
        result = result + result4 + "\n";
        result = result + "\n";
        return result;
    }

    public String getPc() {
        return pc;
    }

    public String getAr() {
        return ar;
    }

    public String getXr() {
        return xr;
    }

    public String getYr() {
        return yr;
    }

    public String getZr() {
        return zr;
    }

    public String getBp() {
        return bp;
    }

    public String getMapl() {
        return mapl;
    }

    public String getMaph() {
        return maph;
    }

    public String getLastOp() {
        return lastOp;
    }

    public String getUnknown() {
        return unknown;
    }

    public String getSr() {
        return sr;
    }

    public boolean isN() {
        return n;
    }

    public boolean isV() {
        return v;
    }

    public boolean isE() {
        return e;
    }

    public boolean isB() {
        return b;
    }

    public boolean isD() {
        return d;
    }

    public boolean isI() {
        return i;
    }

    public boolean isZ() {
        return z;
    }

    public boolean isC() {
        return c;
    }

    public String getSp() {
        return sp;
    }

    public String toNumeralString(boolean bool) {
         return bool ? "1" : "0";
    }

    public boolean isEmpty() {
        if (result4 == null)
            return true;
        return false;
    }

    public String getRegisterTitle() {
        return "PC   AR XR YR ZR BP SP   MAPL MAPH LAST-OP     SR NVEBDIZC";
    }

}
