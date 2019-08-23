package edu.java.core.sort.external.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Converter of exception stsckTrace to convenient string
 */
public class StackTraceExtractor {
    @SuppressWarnings("WeakerAccess")
    public static String process(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
