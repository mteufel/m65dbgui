package org.mega65.m65dbgui.events;

public class GenericEvent {

    public static final String GENERIC_EVENT_MEMORY_VIEWER_MANUAL_REFRESH = "GenericEventMemoryViewerManualRefresh";
    public static final String GENERIC_EVENT_MAPPING_REFRESH = "GenericEventMappingRefresh";
    public static final String GENERIC_EVENT_MAPPING_HAS_BEEN_REFRESHED = "GenericEventMappingRefreshIsDone";

    String event;
    Object data;

    public GenericEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
