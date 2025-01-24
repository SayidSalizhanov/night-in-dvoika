package ru.itis.nightindvoika.action.defender;

import lombok.Setter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.mainClasses.GameEngine;

@Setter
public class PlaySoundAction implements GameProcessAction {
    private int position;

    @Override
    public void doSomeAction(GameEngine gameEngine) {
        gameEngine.soundOnCamera(position);
    }
}
