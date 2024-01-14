package org.mega65.m65dbgui.domain;

import org.mega65.m65dbgui.util.Util;

public record Mapping(String mappingType, String type, String text, long startAdr, long endAdr , long offset, long sourceAdr) {

    public final static String MAPPING_TYPE_MAPH = "MAPH";
    public final static String MAPPING_TYPE_MAPL = "MAPL";
    public final static String MAPPING_TYPE_D030 = "$D030";
    public final static String MAPPING_TYPE_01 = "$01";

    @Override
    public String toString() {
        return mappingType + " " + type + " " + text + ": " + Util.toHex(startAdr, 4) + "-" + Util.toHex(endAdr, 4) + " Offset: " + Util.toHex(offset, 5) +  "(" + offset + ")" + " Source: " + Util.friendlyHex(Util.toHex(sourceAdr, 7)) + " (" + sourceAdr + ")";

    }
}

