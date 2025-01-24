package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.action.main.StartGameAction;
import ru.itis.nightindvoika.controllers.MainMenuController;
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

//        this.gameEngine.fastGameParameters();
        this.gameEngine.setEntitiesAndUtils();
//        this.gameEngine.fastEntities();
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

    public void doSomeAction(Action action) {
        if (action instanceof GameProcessAction) {
            Platform.runLater(() -> {
                action.doSomeAction(gameEngine);
            });
        }
        else if (action instanceof StartGameAction) {
            Platform.runLater(() -> {
                MainMenuController mainMenuController = (MainMenuController) loadersUtil.controllers.get("MainMenuController");
                mainMenuController.startGame();
            });
        }
    }
}