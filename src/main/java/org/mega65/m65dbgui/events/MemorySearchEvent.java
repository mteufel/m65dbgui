package org.mega65.m65dbgui.events;

public class MemorySearchEvent {

    String search;

    public MemorySearchEvent(String search) {
        this.search=search;
    }

    public String getSearch() {
        return search;
    }
}
