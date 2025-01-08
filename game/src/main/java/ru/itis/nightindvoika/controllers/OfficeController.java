package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import ru.itis.nightindvoika.App;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.StringCreator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

@Data
public class OfficeController implements Initializable {

    private Office office;

    @FXML
    ImageView backgroundImageView;
    @FXML
    Button putOnMaskButton, camerasButton;
    @FXML
    Text text;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        office = GameEngine.getInstance().getOffice();
        display();
    }

    public void display() {

        String fileName = StringCreator.createPathImage(office.getAttackEntities(), office.getPosition());

        if (office.isHoldMaskStatus()) {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/mask/%s.png".formatted(getOffice().getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Снять маску");

            camerasButton.setVisible(false);
            camerasButton.setManaged(false);
        }
        else {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(getOffice().getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Надеть маску");

            camerasButton.setVisible(true);
            camerasButton.setManaged(true);
        }
    }

    public void switchMaskMode(ActionEvent event) {
        office.switchMaskMode();
        display();
    }

    public void openCameras(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = App.loaders.get("camera");
        root = App.roots.get("camera");

        CameraController cameraController = loader.getController();
        cameraController.playMediaOpenCameras();
        cameraController.display(1);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
