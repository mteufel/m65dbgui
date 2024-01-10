package org.mega65.m65dbgui.operations;

import java.io.BufferedReader;
import java.io.IOException;

public interface Operation {

    String getCommand();

    void operate(BufferedReader reader) throws IOException;

    boolean isEmpty();

}
