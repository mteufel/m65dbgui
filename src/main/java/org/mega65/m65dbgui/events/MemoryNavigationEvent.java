package org.mega65.m65dbgui.events;

public class MemoryNavigationEvent {

    public static final String NAVIGATION_START = "start";
    public static final String NAVIGATION_END = "end";
    public static final String NAVIGATION_NEXT = "next";
    public static final String NAVIGATION_PREVIOUS = "previous";

    String to = null;
    long increment = 0x100;

    public MemoryNavigationEvent(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setIncrement(long increment) {
        this.increment = increment;
    }

    public long getIncrement() {
        return increment;
    }

    @Override
    public String toString() {
        return "MemoryNavigationEvent->" + to;
    }
}
