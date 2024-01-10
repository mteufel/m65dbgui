package org.mega65.m65dbgui.util;

import java.awt.*;

public class ColorHolder {

    public static final String green      = "#a4be8f";
    public static final String lightGrey  = "#d8dee8";
    public static final String blue       = "#83a1bf";
    public static final String purple     = "#b38eac";
    public static final String darkGrey   = "#626e86";
    public static final String darkGrey2  = "#81858c";

    public static final String red        = "#bf616a";

    public static Color getColor(String color) {
        return Color.decode(color);
    }

}
