package ru.itis.nightindvoika.mainClasses;

import java.io.Serializable;

public class GameEngineInstance implements Serializable {
    private static GameEngine gameEngine;

//    public static GameEngine getGameEngine() {
//        if (gameEngine == null) {
//            gameEngine = new GameEngine();
//        }
//        return gameEngine;
//    }

    public static GameEngine newGameEngine() {
        return new GameEngine();
    }

    public static void clearDataInEngine() {
        gameEngine = null;
    }
}
