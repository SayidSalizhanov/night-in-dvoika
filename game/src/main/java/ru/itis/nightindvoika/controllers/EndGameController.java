package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.itis.nightindvoika.util.LoadersUtil;

public class EndGameController {
    @FXML
    Button menuButton;

    public void openMenu(ActionEvent event) {
        LoadersUtil.loadStartGame();
    }
}
