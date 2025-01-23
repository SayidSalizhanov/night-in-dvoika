package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;

public class App extends Application {
    private static final GameEngine gameEngine = GameEngineInstance.getGameEngine();

    @Override
    public void start(Stage stage) throws IOException {
        LoadersUtil.loadFXMLLoaderStartGame();
        LoadersUtil.setPrimaryStage(stage);

        LoadersUtil.loadStartGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static GameEngine getEngine() {
        return gameEngine;
    }
}