package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;

public class StartGameController {
    @FXML
    Button startButton;

    public void startGame(ActionEvent event) throws IOException {
        LoadersUtil.loadFxmlLoaders();

        LoadersUtil.loadMainMenu();
        GameEngineInstance.getGameEngine().startGame();
    }
}
