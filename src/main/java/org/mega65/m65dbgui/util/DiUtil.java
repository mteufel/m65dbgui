package org.mega65.m65dbgui.util;

import jakarta.enterprise.inject.se.SeContainer;

public class DiUtil {

    public static Object create(Class clazz) {
        SeContainer container = SeContainerSingleton.getInstance().getSeContainer();
        return container.select(clazz).get();
    }

    public static void fireEvent(Object obj) {
        SeContainer container = SeContainerSingleton.getInstance().getSeContainer();
        container.getBeanManager().getEvent().fire(obj);
    }

}
