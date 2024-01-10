package org.mega65.m65dbgui.util;

import jakarta.enterprise.event.Observes;
import org.mega65.m65dbgui.operations.M65Reset;
import org.mega65.m65dbgui.operations.Operation;
import org.slf4j.Logger;

public class OperationsToConsoleObserver  {

    Logger logger = Util.getLogger(OperationsToConsoleObserver.class);

    public void update(@Observes Operation operation) {
        if (operation instanceof M65Reset) {
            logger.info(operation.toString());
        }


    }
}
