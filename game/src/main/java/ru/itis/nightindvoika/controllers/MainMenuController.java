package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.Data;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.LoadersUtil;

@Data
public class MainMenuController implements Controller {
    private GameEngine gameEngine;
    private LoadersUtil loadersUtil;

    @FXML
    Button defenderButton;
    @FXML
    Button attackerButton;
    @FXML
    Text infoText;

    public void startGame() {
        gameEngine.startGame();
        infoText.setVisible(false);
        defenderButton.setDisable(false);
        attackerButton.setDisable(false);
    }

    public void startFromOfficeByDefender(ActionEvent event) {
        loadersUtil.loadOffice();
    }

    public void startFromRadarByAttacker(ActionEvent event) {
        loadersUtil.loadRadar();
    }
}
