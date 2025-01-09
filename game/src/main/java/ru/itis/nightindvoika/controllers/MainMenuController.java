package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Data;
import ru.itis.nightindvoika.util.LoadersUtil;

@Data
public class MainMenuController {

    @FXML
    Button defenderButton;
    @FXML
    Button attackerButton;

    public void startFromOfficeByDefender(ActionEvent event) {
        LoadersUtil.loadOffice(event);
    }

    public void startFromRadarByAttacker(ActionEvent event) {
        LoadersUtil.loadRadar(event);
    }
}
