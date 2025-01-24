package ru.itis.nightindvoika.action.defender;

import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.mainClasses.GameEngine;

public class SwitchMaskModeAction implements Action {
    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.getDefender().switchMaskMode();
    }
}
