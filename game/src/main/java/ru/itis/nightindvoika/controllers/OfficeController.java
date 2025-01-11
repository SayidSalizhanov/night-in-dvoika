package ru.itis.nightindvoika.controllers;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Setter;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.players.Defender;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.StringCreator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class OfficeController implements Initializable {

    private Office office;
    @Setter
    private int nextCameraViewPosition;
    private Defender defender;

    @FXML
    ImageView backgroundImageView;
    @FXML
    Button putOnMaskButton, camerasButton;
    @FXML
    Text text;
    @FXML
    Button menuButton;
    @FXML
    Button radarButton;

    private final Media camerasOpenSound = new Media(getClass().getResource("/static/sounds/office/camerasOpenV2.mp3").toExternalForm());
    private final Media putOnMaskSound = new Media(getClass().getResource("/static/sounds/office/putOnMask.mp3").toExternalForm());
    private final Media putDownMaskSound = new Media(getClass().getResource("/static/sounds/office/putDownMask.mp3").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        office = GameEngineInstance.getGameEngine().getOffice();
        defender = GameEngineInstance.getGameEngine().getDefender();
    }

    public void display() {

        String fileName = StringCreator.createPathImage(office.getAttackEntities().values().stream().toList(), office.getPosition());

        if (office.isHoldMaskStatus()) {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/mask/%s.png".formatted(office.getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Снять маску");

            camerasButton.setVisible(false);
            camerasButton.setDisable(true);

            menuButton.setVisible(false);
            menuButton.setDisable(true);

            radarButton.setVisible(false);
            radarButton.setDisable(true);
        }
        else {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(office.getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Надеть маску");

            camerasButton.setVisible(true);
            camerasButton.setDisable(false);

            menuButton.setVisible(true);
            menuButton.setDisable(false);

            radarButton.setVisible(true);
            radarButton.setDisable(false);
        }

        radarButton.setDisable(!defender.isRadarVisibleStatus());
    }

    public void activateRadar(ActionEvent event) {
        radarButton.setDisable(true);
        defender.radarVisible();

        LoadersUtil.loadRadarForDefender(event);
    }

    public void switchMaskMode(ActionEvent event) {
        if (office.isHoldMaskStatus()) playMediaPutDownMask();
        else playMediaPutOnMask();

        office.switchMaskMode();
        display();
    }

    public void openCameras(ActionEvent event) throws IOException, InterruptedException {
        playMediaOpenCameras();
        LoadersUtil.loadCamera(event, nextCameraViewPosition);
    }

    public void backToMenu(ActionEvent event) {
        LoadersUtil.loadMainMenu(event);
    }

    private void playMediaOpenCameras() {
        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(camerasOpenSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }

    private void playMediaPutOnMask() {
        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(putOnMaskSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }

    private void playMediaPutDownMask() {
        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(putDownMaskSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }
}
