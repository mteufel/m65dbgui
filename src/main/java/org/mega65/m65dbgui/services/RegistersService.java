package org.mega65.m65dbgui.services;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Adressspace;
import org.mega65.m65dbgui.domain.Register;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

@Singleton
public class RegistersService {

    public final static int DB0 = 0;
    public final static int DB1 = 1;
    public final static int DB2 = 2;
    public final static int DB3 = 3;
    public final static int DB4 = 4;
    public final static int DB5 = 5;
    public final static int DB6 = 6;
    public final static int DB7 = 7;

    List<Register> registers = new ArrayList<>();
    Logger logger = Util.getLogger(RegistersService.class);
    @Inject State state;

    //HEX,                          DB7-DB7                                                 ,DESCRIPTION (separated by ; )
    //D030,ROME,    ,CROM9    ,ROMC     ,ROMA      ,ROM8     ,PAL      ,EXTSYNC,  CRAM2K    ,ROME=Map C65 ROM $E000;CROM9=Select between C64 and C65 charset;ROMC=Map C65 ROM $C000;ROMA=Map C65 ROM $A000;ROM8= Map C65 ROM $8000;PAL=Use PALETTE ROM (0) or RAM (1) entries for colours 0 - 15;EXTSYNC=Enable external video sync (genlock input);CRAM2K=Map 2nd KB of colour RAM $DC00-$DFFF
    //....


    public List<Register> getRegisters() {
        return registers;
    }

    public Register getRegister(String hex) {
        Register reg;
        try {
            return registers.stream().filter( r -> r.hex().equals(hex.toUpperCase()) ).toList().get(0);
        } catch (Exception e) {
            Map<String,String> desc = new HashMap<>();
            desc.put("-","");
            reg = new Register("GENERAL","-","-","-","-","-","-","-","-","-",desc);
        }
        return reg;
    }

    public boolean checkBit(int bit, Long value) {
        return BigInteger.valueOf(value).testBit(bit);
    }
    public String getBit(int bit, Long value) {
        if (checkBit(bit, value)) {
            return "1";
        }
        return "0";
    }

    public void loadRegisters() {
        registers = load();
    }

    public void loadRegistersVic2() {
        List<Register> regs = load(); // load all
        regs.removeIf( r -> r.type().equals("VIC-III"));
        regs.removeIf( r -> r.type().equals("VIC-IV"));
        this.registers = regs;
    }
    public void loadRegistersVic3() {
        List<Register> regs = load(); // load all
        regs.removeIf( r -> r.type().equals("VIC-III"));
        regs.removeIf( r -> r.type().equals("VIC-IV"));

        List<Register> onlyVic3 = load(); // load all
        onlyVic3.removeIf( r -> !r.type().equals("VIC-III")); // remove everything except VIC-III

        onlyVic3.forEach( r -> {
            int idx = index(regs, r.hex());
            if (idx > -1) {
                regs.remove(idx);
            }
            regs.add(r);
        });

        this.registers = regs;
    }

    public void loadRegistersVic4() {
        List<Register> regs = load(); // load all
        regs.removeIf( r -> r.type().equals("VIC-III"));
        regs.removeIf( r -> r.type().equals("VIC-IV"));

        List<Register> onlyVic3 = load(); // load all
        onlyVic3.removeIf( r -> !r.type().equals("VIC-III")); // remove everything except VIC-III

        onlyVic3.forEach( r -> {
            int idx = index(regs, r.hex());
            if (idx > -1) {
                regs.remove(idx);
                regs.add(r);
            }
            regs.add(r);
        });

        List<Register> onlyVic4 = load(); // load all
        onlyVic4.removeIf( r -> !r.type().equals("VIC-IV")); // remove everything except VIC-III

        onlyVic4.forEach( r -> {
            int idx = index(regs, r.hex());
            if (idx > -1) {
                regs.remove(idx);
            }
            regs.add(r);
        });

        this.registers = regs;
    }


    private int index(List<Register> list1, String addr) {
        for (Register r : list1) {
            if (r.hex().equals(addr))
                return list1.indexOf(r);
        }
        return -1;

    }

    private List<Register> load() {
        List<Register> regs = new ArrayList<>();
        try {
            InputStream is = this.getClass().getResourceAsStream("/db/registers.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";

            while (line != null) {
                if (!line.equals("") && !line.startsWith("TYPE") && !line.startsWith(";") ) {
                    String[] r = line.split(",");
                    String[] d = r[10].trim().split(";");
                    Map<String, String> desc = new HashMap<>();
                    Arrays.stream(d).toList().forEach( description -> {
                        String key = description.split("=")[0];
                        String value = description.split("=")[1];
                        desc.put(key, value);
                    });
                    String hexFrom = r[1].trim();
                    String hexTo = r[1].trim();
                    if (r[1].contains("-")) {
                        hexFrom = r[1].trim().split("-")[0];
                        hexTo = r[1].trim().split("-")[1];
                    }
                    for (long l = Util.fromHex(hexFrom); l <= Util.fromHex(hexTo) ; l++) {
                        regs.add(new Register(r[0].trim(),Util.toHex(l),r[2].trim(),r[3].trim(),r[4].trim(),r[5].trim(),r[6].trim(),r[7].trim(),r[8].trim(),r[9].trim(), desc));
                    }

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regs;
    }


}

