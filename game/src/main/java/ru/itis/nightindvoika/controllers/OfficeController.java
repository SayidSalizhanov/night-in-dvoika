package ru.itis.nightindvoika.controllers;

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
    @FXML
    Button electricShockButton;
    @FXML
    Button refreshButton;

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

        if (defender.isParalyzeStatus()) {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(office.getSourcePath(), fileName)))
            ));

            office.setHoldMaskStatus(false);

            hideButton(putOnMaskButton);
            hideButton(camerasButton);
            hideButton(menuButton);
            hideButton(radarButton);
            hideButton(electricShockButton);

            text.setText("Паралич");
        }
        else {
            text.setText(null);

            if (office.isHoldMaskStatus()) {
                backgroundImageView.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("%s/mask/%s.png".formatted(office.getSourcePath(), fileName)))
                ));

                putOnMaskButton.setText("Снять маску");

                hideButton(camerasButton);
                hideButton(menuButton);
                hideButton(radarButton);
                hideButton(electricShockButton);
                showButton(putOnMaskButton);
            } else {
                backgroundImageView.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(office.getSourcePath(), fileName)))
                ));

                putOnMaskButton.setText("Надеть маску");

                showButton(camerasButton);
                showButton(menuButton);
                showButton(radarButton);
                showButton(electricShockButton);
                showButton(putOnMaskButton);
            }

            radarButton.setDisable(!defender.isRadarVisibleAbilityStatus());
            electricShockButton.setDisable(!defender.isElectricShockAbilityStatus());
        }
    }

    public void activateRadar(ActionEvent event) {
        radarButton.setDisable(true);
        defender.radarVisible();

        LoadersUtil.loadRadarForDefender(event);
    }

    public void activateElectricShock(ActionEvent event) {
        electricShockButton.setDisable(true);
        defender.electricShock();

        display();
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

    public void refresh(ActionEvent event) {
        display();
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

    private void hideButton(Button button) {
        button.setVisible(false);
        button.setDisable(true);
    }

    private void showButton(Button button) {
        button.setVisible(true);
        button.setDisable(false);
    }
}
