package ru.itis.nightindvoika.action;

import ru.itis.nightindvoika.mainClasses.GameEngine;

import java.io.Serializable;

public interface Action extends Serializable {

    void doSomeAction(GameEngine gameEngine);
}
