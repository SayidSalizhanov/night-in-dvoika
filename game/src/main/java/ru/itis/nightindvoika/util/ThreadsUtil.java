package ru.itis.nightindvoika.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ThreadsUtil implements Serializable {
    public final Map<String, Thread> attackEntitiesThreads = new HashMap<>();
    public final Map<String, Thread> camerasThreads = new HashMap<>();
    public final Map<String, Thread> attackerThreads = new HashMap<>();
    public final Map<String, Thread> defenderThreads = new HashMap<>();
    public final Map<String, Thread> timerThreads = new HashMap<>();

    public void interruptAllAttackEntitiesThreads() {
        for (Thread thread : attackEntitiesThreads.values()) {
            thread.interrupt();
        }
    }

    public void interruptAllCamerasThreads() {
        for (Thread thread : camerasThreads.values()) {
            thread.interrupt();
        }
    }

    public void interruptAllAttackerThreads() {
        for (Thread thread : attackerThreads.values()) {
            thread.interrupt();
        }
    }

    public void interruptAllDefenderThreads() {
        for (Thread thread : defenderThreads.values()) {
            thread.interrupt();
        }
    }

    public void interruptAllTimerThreads() {
        for (Thread thread : timerThreads.values()) {
            thread.interrupt();
        }
    }

    public void interruptAllThreads() {
        interruptAllAttackEntitiesThreads();
        interruptAllCamerasThreads();
        interruptAllAttackerThreads();
        interruptAllDefenderThreads();
        interruptAllTimerThreads();
    }

    public void clearThreadMaps() {
        attackEntitiesThreads.clear();
        camerasThreads.clear();
        attackerThreads.clear();
        defenderThreads.clear();
        timerThreads.clear();
    }
}
