package org.mega65.m65dbgui.domain;

import org.mega65.m65dbgui.util.Util;

public class Byte {

    private long addr;
    private long value;

    public Byte(long addr, long value) {
        this.addr = addr;
        this.value = value;
    }

    public long getAddr() {
        return addr;
    }

    public long getValue() {
        return value;
    }

    public static Byte parseByte(long addr, String value) {
        return new Byte(addr, Long.parseLong(value, 16 ));
    }
    public static Byte parseByte(String addr, String value) {
        return new Byte(Util.fromHex(addr), Long.parseLong(value, 16 ));
    }
    public static Byte parseByte(String addr, long value) {
        return new Byte(Util.fromHex(addr), value);
    }
    public static Byte parseByte(long addr, long value) {
        return new Byte(addr, value);
    }

}
