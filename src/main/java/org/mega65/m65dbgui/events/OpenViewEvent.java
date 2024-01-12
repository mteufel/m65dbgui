package org.mega65.m65dbgui.events;

public class OpenViewEvent {

    public static String TYPE_DISASSEMBLY = "disassembly";
    public static String TYPE_MAPPING = "mapping";
    public static String TYPE_REGISTERS_DETAIL = "registersDetail";

    String data;
    String type;

    public OpenViewEvent(String data, String type) {
        this.data = data;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
