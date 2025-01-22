package ru.itis.nightindvoika.action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActionQueue {
    public static Queue<Action> actionQueue = new ConcurrentLinkedQueue<>();
}
