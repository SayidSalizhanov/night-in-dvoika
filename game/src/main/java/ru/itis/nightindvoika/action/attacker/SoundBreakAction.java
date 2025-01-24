package ru.itis.nightindvoika.action.attacker;

import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.mainClasses.GameEngine;

public class SoundBreakAction implements Action {
    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.soundBreakAllCameras(gameEngine.getSoundBreakByAttackerInSeconds());
    }
}
