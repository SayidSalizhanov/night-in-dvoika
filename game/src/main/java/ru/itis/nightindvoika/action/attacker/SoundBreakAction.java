package ru.itis.nightindvoika.action.attacker;

import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.mainClasses.GameEngine;

public class SoundBreakAction implements GameProcessAction {
    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.soundBreakAllCameras(gameEngine.getSoundBreakByAttackerInSeconds());
    }
}
