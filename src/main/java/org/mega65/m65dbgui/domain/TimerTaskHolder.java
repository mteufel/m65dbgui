package org.mega65.m65dbgui.domain;

import java.util.TimerTask;

public class TimerTaskHolder {

    private String id;
    private TimerTask timerTask;
    private long delay = -1;
    private long period = -1;

    public TimerTaskHolder(String id, TimerTask timerTask, long delay, long period) {
        this.id = id;
        this.timerTask = timerTask;
        this.delay = delay;
        this.period = period;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public String getId() {
        return id;
    }

    public long getDelay() {
        return delay;
    }

    public long getPeriod() {
        return period;
    }

}
