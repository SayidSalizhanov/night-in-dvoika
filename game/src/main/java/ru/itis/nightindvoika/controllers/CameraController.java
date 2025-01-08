package ru.itis.nightindvoika.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.StringCreator;

import java.io.File;
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

    private final Media cameraSwapSound = new Media(getClass().getResource("/static/sounds/cameras/cameraSwap.mp3").toExternalForm());
    private final Media camerasOpenSound = new Media(getClass().getResource("/static/sounds/cameras/camerasOpenV2.mp3").toExternalForm());
    private final Media camerasCloseSound = new Media(getClass().getResource("/static/sounds/cameras/camerasClose.mp3").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cameras = GameEngine.getInstance().getCameras();

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
    }

    public void display(int position) {
        playMedia(cameraSwapSound);

        String fileName = StringCreator.createPathImage(GameEngine.getInstance().getAttackEntities(), position);

        cameraImageView.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("%s/%s.png".formatted(cameras.get(position-1).getSourcePath(), fileName)))
        ));

        currentViewPosition = position;
    }

    public void playMedia(Media media) {
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void playMediaOpenCameras() {
        MediaPlayer mediaPlayer = new MediaPlayer(camerasOpenSound);
        mediaPlayer.play();
    }

    public void playMediaCloseCameras() {
        MediaPlayer mediaPlayer = new MediaPlayer(camerasCloseSound);
        mediaPlayer.play();
    }
}
