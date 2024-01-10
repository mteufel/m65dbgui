package org.mega65.m65dbgui.events;

public class OpenMemoryViewerEvent {

    public static final int MODE_REFRESH_FAST = 1;
    public static final int MODE_REFRESH_NORMAL = 2;
    public static final int MODE_REFRESH_MANUAL = 3;

    private long offset;
    private long adrStart;
    private long adrEnd;
    private long adr;
    private String title;
    private boolean closeable;
    private int refreshMode;

    public OpenMemoryViewerEvent(boolean closeable, String title, long offset, long adrStart, long adrEnd, long adr, int refreshMode) {
        this.closeable = closeable;
        this.title = title;
        this.offset = offset;
        this.adrStart = adrStart;
        this.adrEnd = adrEnd;
        this.adr = adr;
        this.refreshMode = refreshMode;
    }

    public OpenMemoryViewerEvent(boolean closeable, String title, long offset, long adrStart, long adrEnd, int refreshMode) {
        this.closeable = closeable;
        this.title = title;
        this.offset = offset;
        this.adrStart = adrStart;
        this.adrEnd = adrEnd;
        this.adr = adrStart;
        this.refreshMode = refreshMode;
    }

    public long getAdrStart() {
        return adrStart;
    }

    public long getAdrEnd() {
        return adrEnd;
    }

    public long getAdr() {
        return adr;
    }

    public String getTitle() {
        return title;
    }

    public long getOffset() {
        return offset;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public int getRefreshMode() {
        return refreshMode;
    }
}
