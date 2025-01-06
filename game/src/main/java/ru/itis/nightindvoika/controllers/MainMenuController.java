package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;

@Data
public class MainMenuController {

    @FXML
    Button defenderButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void startFromOfficeByDefender(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ru/itis/nightindvoika/defender/office.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
