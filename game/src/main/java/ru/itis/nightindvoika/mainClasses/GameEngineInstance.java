package ru.itis.nightindvoika.mainClasses;

public class GameEngineInstance {
    private static GameEngine gameEngine;

    public static GameEngine getGameEngine() {
        if (gameEngine == null) {
            gameEngine = new GameEngine();
        }
        return gameEngine;
    }

    public static void clearDataInEngine() {
        gameEngine = null;
    }
}
