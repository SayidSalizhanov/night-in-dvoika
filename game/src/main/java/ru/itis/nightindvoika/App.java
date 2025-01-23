package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.ThreadsUtil;

import java.io.IOException;

@Getter
public class App extends Application {
    private GameEngine gameEngine;
    private ThreadsUtil threadsUtil;
    private LoadersUtil loadersUtil;

    public App(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        threadsUtil = new ThreadsUtil();
        this.gameEngine.setThreadsUtil(threadsUtil);

        loadersUtil = new LoadersUtil();
        this.gameEngine.setLoadersUtil(loadersUtil);
        loadersUtil.setGameEngine(gameEngine);

        this.gameEngine.setEntitiesAndUtils();
    }

    @Override
    public void start(Stage stage) throws IOException {
        loadersUtil.loadFXMLLoaderStartGame();
        loadersUtil.setPrimaryStage(stage);

        loadersUtil.loadStartGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}