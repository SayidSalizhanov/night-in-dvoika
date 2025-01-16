package ru.itis.nightindvoika.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadsUtil {
    public static final Map<String, Thread> attackEntitiesThreads = new HashMap<>();
    public static final Map<String, Thread> camerasThreads = new HashMap<>();
    public static final Map<String, Thread> attackerThreads = new HashMap<>();
    public static final Map<String, Thread> defenderThreads = new HashMap<>();
    public static final Map<String, Thread> timerThreads = new HashMap<>();

    public static void interruptAllAttackEntitiesThreads() {
        for (Thread thread : attackEntitiesThreads.values()) {
            thread.interrupt();
        }
    }

    public static void interruptAllCamerasThreads() {
        for (Thread thread : camerasThreads.values()) {
            thread.interrupt();
        }
    }

    public static void interruptAllAttackerThreads() {
        for (Thread thread : attackerThreads.values()) {
            thread.interrupt();
        }
    }

    public static void interruptAllDefenderThreads() {
        for (Thread thread : defenderThreads.values()) {
            thread.interrupt();
        }
    }

    public static void interruptAllTimerThreads() {
        for (Thread thread : timerThreads.values()) {
            thread.interrupt();
        }
    }

    public static void interruptAllThreads() {
        interruptAllAttackEntitiesThreads();
        interruptAllCamerasThreads();
        interruptAllAttackerThreads();
        interruptAllDefenderThreads();
        interruptAllTimerThreads();
    }

    public static void clearThreadMaps() {
        attackEntitiesThreads.clear();
        camerasThreads.clear();
        attackerThreads.clear();
        defenderThreads.clear();
        timerThreads.clear();
    }
}
