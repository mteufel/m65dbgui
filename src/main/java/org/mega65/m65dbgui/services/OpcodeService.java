package org.mega65.m65dbgui.services;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.addressing.Addressing;
import org.mega65.m65dbgui.domain.Opcode;
import org.mega65.m65dbgui.util.DiUtil;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Singleton
public class OpcodeService {

    List<Opcode> opcodes;
    Logger logger = Util.getLogger(OpcodeService.class);

    @PostConstruct
    public void postConstruct() {
        Opcode opcode;
        BufferedReader reader;
        opcodes = new ArrayList<>();

        try {
            InputStream is = this.getClass().getResourceAsStream("/db/opcodes-4510.txt");
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine();

            while (line != null) {
                if (!line.startsWith("OP")) {
                    String[] s = line.split(",");
                    if (s.length==6) {
                        opcode = new Opcode(s[0].trim(),s[1].trim(),Integer.parseInt(s[2].trim()),Integer.parseInt(s[3].trim()),s[4].trim(),loadAddressing(s[5].trim()),"");
                    } else {
                        opcode = new Opcode(s[0].trim(),s[1].trim(),Integer.parseInt(s[2].trim()),Integer.parseInt(s[3].trim()),s[4].trim(),loadAddressing(s[5].trim()),s[6].trim());
                    }

                    opcodes.add(opcode);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Addressing loadAddressing(String addressingName)  {
        try {
            Addressing addressing = (Addressing) DiUtil.create(Class.forName("org.mega65.m65dbgui.addressing."+addressingName));

            String descriptionFile = "/db/addressing/" + addressingName + ".txt";
            try {
                InputStream is = this.getClass().getResourceAsStream(descriptionFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                addressing.setShortcut1(reader.readLine());
                addressing.setShortcut2(reader.readLine());
                addressing.setTitle(reader.readLine());
                String description = "";
                String line = reader.readLine();;
                while (line != null) {
                    description = description + line + "\n";
                    line = reader.readLine();
                }
                addressing.setDescription(description);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return addressing;
        } catch (ClassNotFoundException e) {
            logger.error("Addressing can not be instantiaded, Class not found (" + addressingName +  ")");
            return null;
        }
    }

    public Opcode getOpcodeByCode(long op) {
        return getOpcodeByCode(Util.toHex(op,2));
    }

    public Opcode getOpcodeByCode(String op) {
        try {
            return opcodes.stream().filter( e -> op.equals(e.getOp()) ).findFirst().get();
        } catch (NoSuchElementException e) {
            return new Opcode("","???",0,0,"",null,"");
        }
    }

    public Opcode getOpcodeByInstruction(String instruction) {
        try {
            return opcodes.stream().filter( e -> instruction.equals(e.getInstruction()) ).findFirst().get();
        } catch (NoSuchElementException e) {
            return new Opcode("","???",0,0,"",null,"");
        }
    }

    public List<Opcode> getOpcodes() {
        return opcodes;
    }
}
