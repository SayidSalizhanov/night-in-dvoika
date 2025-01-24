package ru.itis.nightindvoika.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import ru.itis.nightindvoika.controllers.*;
import ru.itis.nightindvoika.mainClasses.GameEngine;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoadersUtil implements Serializable {
    @Setter
    public GameEngine gameEngine;
    @Setter
    public Stage primaryStage;

    public final Map<String, FXMLLoader> loaders = new HashMap<>();
    public final Map<String, Scene> scenes = new HashMap<>();

    public final Map<String, Controller> controllers = new HashMap<>();

    public void loadFxmlLoaders() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/main_menu.fxml"));
        loaders.put("mainMenu", loader);
        scenes.put("mainMenu", new Scene(loader.load()));

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/defender/office.fxml"));
        loaders.put("office", loader);
        scenes.put("office", new Scene(loader.load()));

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/defender/camera.fxml"));
        loaders.put("camera", loader);
        scenes.put("camera", new Scene(loader.load()));

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/attacker/radar.fxml"));
        loaders.put("radar", loader);
        scenes.put("radar", new Scene(loader.load()));

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/defender/radar.fxml"));
        loaders.put("defenderRadar", loader);
        scenes.put("defenderRadar", new Scene(loader.load()));

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/endGame.fxml"));
        loaders.put("endGame", loader);
        scenes.put("endGame", new Scene(loader.load()));
    }

    public void loadFXMLLoaderStartGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/startGame.fxml"));
        loaders.put("startGame", loader);
        scenes.put("startGame", new Scene(loader.load()));
    }

    public void loadMainMenu() {
        MainMenuController mainMenuController = loaders.get("mainMenu").getController();
        mainMenuController.setLoadersUtil(this);
        mainMenuController.setGameEngine(gameEngine);

        controllers.put("MainMenuController", mainMenuController);

        Scene scene = scenes.get("mainMenu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadRadarForDefender() {
        RadarForDefenderController radarForDefenderController = loaders.get("defenderRadar").getController();
        radarForDefenderController.setGameEngine(gameEngine);
        radarForDefenderController.setLoadersUtil(this);
        radarForDefenderController.displayPreparing();

        controllers.put("RadarForDefenderController", radarForDefenderController);

        Scene scene = scenes.get("defenderRadar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadOffice() {
        loadOffice(1);
    }

    public void loadOffice(int nextCameraViewPosition) {
        OfficeController officeController = loaders.get("office").getController();
        officeController.setGameEngine(gameEngine);
        officeController.setLoadersUtil(this);
        officeController.setNextCameraViewPosition(nextCameraViewPosition);
        officeController.displayPreparing();

        controllers.put("OfficeController", officeController);

        Scene scene = scenes.get("office");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadCamera(int nextCameraViewPosition) {
        CameraController cameraController = loaders.get("camera").getController();
        cameraController.setGameEngine(gameEngine);
        cameraController.setLoadersUtil(this);
        cameraController.displayPreparing(nextCameraViewPosition);

        controllers.put("CameraController", cameraController);

        Scene scene = scenes.get("camera");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadRadar() {
        RadarController radarController = loaders.get("radar").getController();
        radarController.setGameEngine(gameEngine);
        radarController.setLoadersUtil(this);
        radarController.displayPreparing();

        controllers.put("RadarController", radarController);

        Scene scene = scenes.get("radar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadEndGame(boolean winStatus) {
        EndGameController endGameController = loaders.get("endGame").getController();
        endGameController.setLoadersUtil(this);
        endGameController.setGameEngine(gameEngine);
        endGameController.displayPreparing(winStatus);

        controllers.put("EndGameController", endGameController);

        Scene scene = scenes.get("endGame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadStartGame() {
        StartGameController startGameController = loaders.get("startGame").getController();
        startGameController.setLoadersUtil(this);
        startGameController.setGameEngine(gameEngine);

        controllers.put("StartGameController", startGameController);

        Scene scene = scenes.get("startGame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
