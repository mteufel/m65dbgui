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

    @PostConstruct
    public void postConstruct() {

        try {
            InputStream is = this.getClass().getResourceAsStream("/db/registers.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";

            while (line != null) {
                if (!line.equals("") && !line.startsWith("HEX")) {
                    String[] r = line.split(",");
                    String[] d = r[9].trim().split(";");
                    Map<String, String> desc = new HashMap<>();
                    Arrays.stream(d).toList().forEach( description -> {
                        String key = description.split("=")[0];
                        String value = description.split("=")[1];
                        desc.put(key, value);
                    });

                registers.add(new Register(r[0].trim(),r[1].trim(),r[2].trim(),r[3].trim(),r[4].trim(),r[5].trim(),r[6].trim(),r[7].trim(),r[8].trim(), desc));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Register getRegister(String hex) {
        Register reg;
        try {
            reg = registers.stream().filter( r -> r.hex().equals(hex.toUpperCase()) ).toList().get(0);
        } catch (Exception e) {
            Map<String,String> desc = new HashMap<>();
            desc.put("-","");
            reg = new Register("-","-","-","-","-","-","-","-","-",desc);
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

}

