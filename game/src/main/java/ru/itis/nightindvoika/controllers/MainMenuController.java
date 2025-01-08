package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;
import ru.itis.nightindvoika.App;
import ru.itis.nightindvoika.util.LoadersUtil;

import java.io.IOException;

@Data
public class MainMenuController {

    @FXML
    Button defenderButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void startFromOfficeByDefender(ActionEvent event) throws IOException {
        LoadersUtil.loadOffice(event);
    }
}
