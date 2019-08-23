package edu.java.core.sort.external.utils;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Provides slow motions for possibility of debugging and profiling
 */
public class Sleeper {

    private static Logger log = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static int counter;
    private static boolean sleeperEnabled = false;
    private static boolean slowerEnabled = sleeperEnabled;

    /**
     * Form many calls to this function a sleep will occur only once per {@param period} ms.
     * Convenient for slowing of repeatable actions
     */
    public static void touch(int period){
        if (slowerEnabled) {
            if (++counter % period == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, StackTraceExtractor.process(e));
                }
            }
        }
    }

    /**
     * A usual sleep
     *
     * @param ms actual delay
     * @param logEnabled flag to write logs about sleeping
     */
    @SuppressWarnings("WeakerAccess")
    public static void force(int ms, boolean logEnabled){
        if (sleeperEnabled) {
            if (logEnabled) log.log(Level.WARNING, "Sleep for {0} ms", ms);
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, StackTraceExtractor.process(e));
            }
        }
    }

    /**
     * Become countdown with logging of progress
     */
    public static void countDown(int steps, int ms){
        if (sleeperEnabled) {
            log.log(Level.WARNING, "Countdown start");
            for (int i = 5; i > 0; i--) {
                log.log(Level.INFO, "{0}",i);
                Sleeper.force(2000, false);
            }
        }
    }

}
