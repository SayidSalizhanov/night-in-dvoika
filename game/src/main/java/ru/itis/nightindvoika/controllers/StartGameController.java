package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;

@Data
public class StartGameController {
    private GameEngine gameEngine;
    private LoadersUtil loadersUtil;

    @FXML
    Button startButton;

    public void startGame(ActionEvent event) throws IOException {
        loadersUtil.loadFxmlLoaders();

        loadersUtil.loadMainMenu();
        gameEngine.startGame();
    }
}
