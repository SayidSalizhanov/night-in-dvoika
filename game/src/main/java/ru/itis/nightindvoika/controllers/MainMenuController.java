package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Data;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;

@Data
public class MainMenuController {

    @FXML
    Button defenderButton;

    public void startFromOfficeByDefender(ActionEvent event) throws IOException {
        LoadersUtil.loadOffice(event);
    }
}
