package org.mega65.m65dbgui;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.connectors.Connector;
import org.mega65.m65dbgui.connectors.M65SocketConnector;
import org.mega65.m65dbgui.domain.TimerTaskHolder;
import org.mega65.m65dbgui.operations.Operation;
import org.mega65.m65dbgui.util.Receiver;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.util.*;


@Singleton
public class State {

    Connector connector;
    Logger logger = Util.getLogger(State.class);
    Timer timer = new Timer();
    List<TimerTaskHolder> timerTasks = new ArrayList<>();
    List<Receiver> receivers = new ArrayList<>();


    @Inject
    public State(M65SocketConnector m65connector)   {
        this.connector = m65connector;
    }

    public Connector getConnector() {
        return connector;
    }

    public boolean isConnected() {
        return this.connector.isConnected();
    }

    public void connect(boolean connected) {
        this.connector.connect(connected);
    }

    public void executeOperation(@Observes Operation operation) {
        if (isConnected()) {
            Operation result = executeDirect(operation);
            receivers.forEach( r -> r.update(result));
        }
    }

    public Operation executeDirect(Operation operation) {
        if (isConnected()) {
            return connector.sendReceive(operation);
        }
        return null;
    }

    public void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }

    public void addTimerTask(TimerTaskHolder timerTaskHolder) {
        logger.info("adding timerTask: " + timerTaskHolder.getId());
        timerTasks.add(timerTaskHolder);
    }

    //public void startTimerTask(String id, long delay, long period) {
    public void startTimerTask(String id) {
        for (TimerTaskHolder t : timerTasks) {
            if (t.getId().equals(id)) {
                timer.scheduleAtFixedRate(t.getTimerTask(), t.getDelay(), t.getPeriod());
                return;
            }
        }
    }

    public void cancelTimerTask(String id) {
        for (TimerTaskHolder t : timerTasks) {
            if (t.getId().equals(id)) {
                logger.info("cancelling timerTask: " + id);
                t.getTimerTask().cancel();
                timerTasks.remove(t);
                return;
            }
        }
    }

    public boolean hasTimerTask(String id) {
        for (TimerTaskHolder t : timerTasks) {
            if (t.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<TimerTaskHolder> getTimerTasks() {
        return timerTasks;
    }

    public void setPause(boolean pause) {
        if (connector instanceof M65SocketConnector) {
            ((M65SocketConnector) connector).setPause(pause);
        }
    }

}

