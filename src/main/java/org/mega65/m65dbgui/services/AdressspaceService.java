package org.mega65.m65dbgui.services;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.State;
import org.mega65.m65dbgui.domain.Adressspace;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AdressspaceService {

    List<Adressspace> adressspaces = new ArrayList<>();
    Logger logger = Util.getLogger(AdressspaceService.class);
    boolean check;
    @Inject State state;

    //START,END,SIZE,AVAILABLE,DESCRIPTION
    //0000000,0000000,1,NO,CPU I/O Port Data Direction Register
    //....

    @PostConstruct
    public void postConstruct() {

        try {
            InputStream is = this.getClass().getResourceAsStream("/db/adressspace.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";

            while (line != null) {
                if (!line.equals("") && !line.startsWith("START")) {
                    String[] s = line.split(",");
                    adressspaces.add(new Adressspace(s[0],s[1],s[2],s[3],s[4]));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Adressspace> getAdressspaces() {
        return adressspaces.stream().filter( a -> !a.available().equals("ROM") ).toList();
    }

    public List<Adressspace> getROMLayout() {
        return adressspaces.stream().filter( a -> a.available().equals("ROM") ).toList();
    }

    public Adressspace getAdressspaceByAdr(long adr) {
        List<Adressspace> romLayout = getROMLayout();
        return romLayout.stream().filter(a -> {
            long start = Util.fromHex(a.start());
            long end = Util.fromHex(a.end());
            return adr >= start && adr <= end;
        }).toList().get(0);
    }


    public String checkEntry(String entry) {

        entry = entry.replace(".", "");

        check = false;
        if (entry.trim().equals("")) {
            return "";
        }

        if (entry.trim().length()==3) {
            entry = entry + "0000";
        }

        if (entry.trim().length() != 7) {
            return "too less";
        }

        if (Util.isHex(entry)) {
            long valueToCheck = Util.fromHex(entry);
            adressspaces.forEach( a -> {
                if (a.available().equals("YES")) {
                    long start = Util.fromHex(a.start());
                    long end = Util.fromHex(a.end());
                    if (valueToCheck >= start && valueToCheck <= end) {
                        check = true;
                    }
                }

            });
            if (check) {

                String title = ("RAM." + entry.substring(0, 3) + "." + entry.substring(3,4)).toUpperCase();
                if (state.hasTimerTask(title)) {
                    return "a tab is already shown";
                }

                return "ok";
            }
            return "invalid value";

        } else {
            return "Please enter valid hex";
        }
    }

}
