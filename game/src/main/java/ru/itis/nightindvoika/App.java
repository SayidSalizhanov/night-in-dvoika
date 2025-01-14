package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LoadersUtil.loadFxmlLoaders();
        LoadersUtil.setPrimaryStage(stage);
        GameEngineInstance.getGameEngine().startGame();

        LoadersUtil.loadMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}