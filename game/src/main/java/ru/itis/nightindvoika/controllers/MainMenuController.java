package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.LoadersUtil;

@Data
public class MainMenuController {
    private GameEngine gameEngine;
    private LoadersUtil loadersUtil;

    @FXML
    Button defenderButton;
    @FXML
    Button attackerButton;

    public void startFromOfficeByDefender(ActionEvent event) {
        loadersUtil.loadOffice();
    }

    public void startFromRadarByAttacker(ActionEvent event) {
        loadersUtil.loadRadar();
    }
}
