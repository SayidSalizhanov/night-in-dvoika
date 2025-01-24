package ru.itis.nightindvoika.action.defender;

import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.mainClasses.GameEngine;

public class ElectricShockAction implements GameProcessAction {
    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.electricShockAttackEntities(gameEngine.getElectricShockFromDefenderInSeconds());
    }
}
