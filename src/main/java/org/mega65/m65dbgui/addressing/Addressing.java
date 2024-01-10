package org.mega65.m65dbgui.addressing;

import org.mega65.m65dbgui.domain.Disassembly;

public abstract class Addressing {

    String shortcut1;
    String shortcut2;
    String title;
    String description;

    public void setShortcut1(String shortcut1) {
        this.shortcut1 = shortcut1;
    }

    public void setShortcut2(String shortcut2) {
        this.shortcut2 = shortcut2;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortcut1() {
        return shortcut1;
    }

    public abstract String getInstruction(String numberFormat, Disassembly disassembly);


}
