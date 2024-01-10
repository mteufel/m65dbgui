package org.mega65.m65dbgui.util;

import org.mega65.m65dbgui.domain.Disassembly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;


public class Util {

    public static void configureLogging() {
        java.util.logging.Logger julRootLogger = LogManager.getLogManager().getLogger("");
        for ( Handler h : julRootLogger.getHandlers() ) {
            julRootLogger.removeHandler(h);
        }
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        julRootLogger.addHandler(consoleHandler);
    }

    public static long checkLong(Long l) {
        if (l == null) {
            return -1;
        }
        return l.longValue();
    }

    public static String toBinary(long value) {
        // returns 8 bits
        return Long.toBinaryString(0x100 | value).substring(1);
    }

    public static String toBinary2(long value) {
        // returns only 4 bits
        return Long.toBinaryString(0x10 | value).substring(1);
    }

    public static String toHex(long value) {
        return Util.toHex(value, 2);
    }

    public static String toHexNice(long value) {
        String my = toHex(value,7);
        return my.substring(0,3) + "." + my.substring(3,7);
    }

    public static String toHex(long value, int length) {
        if (value == -1) {
            return "";
        }
        String hex = Long.toHexString(value);
        hex = String.format("%" + length + "s", hex).replace(' ', '0');
        hex = hex.toUpperCase();
        return hex;
    }

    public static boolean isHex(String strHex) {
        if (strHex == null) {
            return false;
        }
        strHex.toLowerCase();
        try {
            double d = Long.parseLong(strHex, 16);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Long fromHex(String strHex) {
        if (isHex(strHex)) {
            return Long.parseLong(strHex, 16);
        }
        return Long.valueOf(-1);
    }

    public static String friendlyHex(String string) {
        // formats a 28bit hex string so that it is better to read
        int position = 3;
        String insert = ".";
        // What it does is, matches position characters from the beginning of the string,
        // groups that, and replaces the group with itself ($1) followed by the insert string.
        // Mind the replaceAll, even though there's always one occurrence, because the first parameter must be a regex.
        return string.replaceAll("^(.{" + position + "})", "$1" + insert);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }

}
