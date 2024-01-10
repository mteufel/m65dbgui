package org.mega65.m65dbgui.util;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class SeContainerSingleton {

    private static SeContainerSingleton INSTANCE;
    private SeContainer seContainer;
    private String info = "Initial info class";

    public SeContainerSingleton() {
        seContainer = SeContainerInitializer.newInstance().initialize();
    }

    public static SeContainerSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SeContainerSingleton();
        }
        return INSTANCE;
    }

    public SeContainer getSeContainer() {
        return seContainer;
    }
}




