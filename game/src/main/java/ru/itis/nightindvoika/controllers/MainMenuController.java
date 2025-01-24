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

        // через 10 секунд кнопки возврата в меню закроются
        new Thread(() -> {
            try {
                Thread.sleep(10000);

                OfficeController officeController = ((OfficeController) loadersUtil.controllers.get("OfficeController"));
                RadarController radarController = ((RadarController) loadersUtil.controllers.get("RadarController"));
                if (officeController != null) officeController.hideMenuButton();
                if (radarController != null) radarController.hideMenuButton();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void startFromOfficeByDefender(ActionEvent event) {
        loadersUtil.loadOffice();
    }

    public void startFromRadarByAttacker(ActionEvent event) {
        loadersUtil.loadRadar();
    }
}
