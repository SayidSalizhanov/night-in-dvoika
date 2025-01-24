package ru.itis.nightindvoika.action.attacker;

import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.mainClasses.GameEngine;

public class CamerasBreakAction implements Action {
    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.breakAllCameras(gameEngine.getBreakAllCamerasInSeconds());
    }
}
