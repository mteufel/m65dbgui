package org.mega65.m65dbgui.timers;

import org.mega65.m65dbgui.operations.M65Registers;
import java.util.TimerTask;

import static org.mega65.m65dbgui.util.DiUtil.fireEvent;

public class RegistersTimerTask extends TimerTask {

    public static String REGISTERS_TIMER_TASK_ID = "registersTimerTask";

    @Override
    public void run() {
        fireEvent(new M65Registers());
    }

}
