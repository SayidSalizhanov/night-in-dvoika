package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.GameProcessAction;
import ru.itis.nightindvoika.action.main.StartGameAction;
import ru.itis.nightindvoika.action.main.StartGameAvailableAction;
import ru.itis.nightindvoika.action.main.StartGameNotAvailableAction;
import ru.itis.nightindvoika.controllers.MainMenuController;
import ru.itis.nightindvoika.controllers.StartGameController;
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
//        else if (action instanceof StartGameAvailableAction) {
//            /*
//            цикл while нужен для случая, когда 2-ой игрок подключился к серверу,
//            в этот момент сервер сразу пошлет разрешение на начало игры,
//            но у 2-ого игрока еще не успел прогрузиться StartGameController.
//            В таком случае 2-ой игрок будет ждать прогрузку контроллера,
//            а потом разрешит нажать на кнопку запуска
//            */
//            Platform.runLater(() -> {
//                StartGameController startGameController = (StartGameController) loadersUtil.controllers.get("StartGameController");
//                while (startGameController == null) {
//                    try {
//                        Thread.sleep(500L);
//                        startGameController = (StartGameController) loadersUtil.controllers.get("StartGameController");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                startGameController.enableStartButton();
//            });
//        }
//        else if (action instanceof StartGameNotAvailableAction) {
//            /*
//            логика та же, что и сверху, но это на случай,
//            когда игрок остался один в предстартовом лобби,
//            тогда кнопка Start будет недоступна
//            */
//            Platform.runLater(() -> {
//                StartGameController startGameController = (StartGameController) loadersUtil.controllers.get("StartGameController");
//                while (startGameController == null) {
//                    try {
//                        Thread.sleep(500L);
//                        startGameController = (StartGameController) loadersUtil.controllers.get("StartGameController");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                startGameController.disableStartButton();
//            });
//        }
    }
}