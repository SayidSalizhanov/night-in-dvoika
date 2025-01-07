package ru.itis.nightindvoika.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.StringCreator;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CameraController implements Initializable {

    private List<Camera> cameras;
    private int currentViewPosition;

    @FXML
    ImageView cameraImageView;

    @FXML
    Button camera1, camera2, camera3, camera4, camera5, camera6, camera7, camera8, camera9, camera10, camera11, camera12, camera13, camera14;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cameras = GameEngine.getInstance().getCameras();

        cameras.sort(Comparator.comparingInt(Camera::getPosition));

        camera1.setOnAction(event -> display(1));
        camera2.setOnAction(event -> display(2));
        camera3.setOnAction(event -> display(3));
        camera4.setOnAction(event -> display(4));
        camera5.setOnAction(event -> display(5));
        camera6.setOnAction(event -> display(6));
        camera7.setOnAction(event -> display(7));
        camera8.setOnAction(event -> display(8));
        camera9.setOnAction(event -> display(9));
        camera10.setOnAction(event -> display(10));
        camera11.setOnAction(event -> display(11));
        camera12.setOnAction(event -> display(12));
        camera13.setOnAction(event -> display(13));
        camera14.setOnAction(event -> display(14));

        currentViewPosition = 1;
        display(currentViewPosition);
    }

    public void display(int position) {

        String fileName = StringCreator.createPathImage(GameEngine.getInstance().getAttackEntities(), position);

        cameraImageView.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("%s/%s.png".formatted(cameras.get(position-1).getSourcePath(), fileName)))
        ));

        currentViewPosition = position;
    }
}
