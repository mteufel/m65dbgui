package org.mega65.m65dbgui.connectors;

import org.mega65.m65dbgui.operations.Operation;

public interface Connector {

    void connect(boolean connected);

    boolean isConnected();

    Operation sendReceive(Operation Operation);

   // String getRegisterTitle();

}
