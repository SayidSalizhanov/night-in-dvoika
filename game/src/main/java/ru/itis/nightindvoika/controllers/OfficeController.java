package ru.itis.nightindvoika.controllers;

import javafx.application.Platform;
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
import lombok.Data;
import lombok.Setter;
import ru.itis.nightindvoika.action.defender.ElectricShockAction;
import ru.itis.nightindvoika.action.defender.SwitchMaskModeAction;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.players.Defender;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.StringCreator;
import ru.itis.nightindvoika.util.Timer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Data
public class OfficeController implements Initializable, Controller {
    private GameEngine gameEngine;
    private LoadersUtil loadersUtil;

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
    Text textTimer;
    @FXML
    Button menuButton;
    @FXML
    Button radarButton;
    @FXML
    Button electricShockButton;

    private final Media camerasOpenSound = new Media(getClass().getResource("/static/sounds/office/camerasOpenV2.mp3").toExternalForm());
    private final Media putOnMaskSound = new Media(getClass().getResource("/static/sounds/office/putOnMask.mp3").toExternalForm());
    private final Media putDownMaskSound = new Media(getClass().getResource("/static/sounds/office/putDownMask.mp3").toExternalForm());
    private final Media shockerSound = new Media(getClass().getResource("/static/sounds/office/shocker.mp3").toExternalForm());
    private final Media openRadarSound = new Media(getClass().getResource("/static/sounds/office/openRadar.mp3").toExternalForm());

    @Setter
    private static boolean refreshFlag; // todo убрать статик
    private int timeToRefreshInSeconds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        office = gameEngine.getOffice();
        defender = gameEngine.getDefender();

        timeToRefreshInSeconds = gameEngine.getTimeToRefreshInSeconds();
        refreshFlag = true;

        refreshThreadStart();
    }

    public void displayPreparing() {
        refreshOffice();
    }

    public void refreshOffice() {
        textTimer.setText("%d AM".formatted(Timer.currentHour));

        String fileName = StringCreator.createPathImage(gameEngine.getAttackEntities().values().stream().toList(), office.getPosition());

        if (defender.isParalyzeStatus()) {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(office.getSourcePath(), fileName)))
            ));

            defender.setHoldMaskStatus(false);

            hideButton(putOnMaskButton);
            hideButton(camerasButton);
            hideButton(radarButton);
            hideButton(electricShockButton);

            text.setText("Паралич");
        }
        else {
            text.setText(null);

            if (defender.isHoldMaskStatus()) {
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

    public void refreshThreadStart() {
        // запуск потока который будет обновлять сцену
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (refreshFlag) {
                    try {
                        Thread.sleep(timeToRefreshInSeconds * 1000L);
                    } catch (InterruptedException e) {
                        return null;
                    }
                    Platform.runLater(() -> refreshOffice());
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    public void activateRadar(ActionEvent event) {
        playMediaOpenRadar();

        radarButton.setDisable(true);
        defender.radarVisible();

        loadersUtil.loadRadarForDefender();
    }

    public void activateElectricShock(ActionEvent event) {
        playMediaShocker();

        electricShockButton.setDisable(true);
        defender.electricShock();

        displayPreparing();

        gameEngine.getActionQueue().add(new ElectricShockAction());
    }

    public void switchMaskMode(ActionEvent event) {
        if (defender.isHoldMaskStatus()) playMediaPutDownMask();
        else playMediaPutOnMask();

        defender.switchMaskMode();
        displayPreparing();

        gameEngine.getActionQueue().add(new SwitchMaskModeAction());
    }

    public void openCameras(ActionEvent event) throws IOException, InterruptedException {
        playMediaOpenCameras();
        loadersUtil.loadCamera(nextCameraViewPosition);
    }

    public void backToMenu(ActionEvent event) {
        loadersUtil.loadMainMenu();
    }

    private void playMediaOpenCameras() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(camerasOpenSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }

    private void playMediaPutOnMask() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(putOnMaskSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }

    private void playMediaPutDownMask() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(putDownMaskSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }

    private void playMediaShocker() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(shockerSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }

    private void playMediaOpenRadar() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(openRadarSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
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
