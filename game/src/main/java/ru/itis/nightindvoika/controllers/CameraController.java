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
import ru.itis.nightindvoika.entites.Camera;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.RandomSingleton;
import ru.itis.nightindvoika.util.StringCreator;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class CameraController implements Initializable {

    private final Random random = RandomSingleton.getInstance();

    // внутри GameEngine они уже отсортированы по позиции
    private List<Camera> cameras;
    private int currentViewPosition;

    @FXML
    ImageView cameraImageView;
    @FXML
    Button camera1, camera2, camera3, camera4, camera5, camera6, camera7, camera8, camera9, camera10, camera11, camera12, camera13, camera14;
    @FXML
    Button soundButton;
    @FXML
    Button officeButton;

    private final Media cameraSwapSound = new Media(getClass().getResource("/static/sounds/cameras/camera/cameraSwap.mp3").toExternalForm());
    private final Media camerasCloseSound = new Media(getClass().getResource("/static/sounds/cameras/camerasClose.mp3").toExternalForm());

    private final Media cameraHelloSound = new Media(getClass().getResource("/static/sounds/cameras/camera/hello.mp3").toExternalForm());
    private final Media cameraHiSound = new Media(getClass().getResource("/static/sounds/cameras/camera/hi.mp3").toExternalForm());
    private final Media cameraHahaSound = new Media(getClass().getResource("/static/sounds/cameras/camera/haha.mp3").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cameras = GameEngineInstance.getGameEngine().getCameras();

        // setOnAction для каждой камеры с помощью рефлексии
        for (int i = 1; i <= 14; i++) {
            try {
                Field field = this.getClass().getDeclaredField("camera%d".formatted(i));
                field.setAccessible(true);

                Button button = (Button) field.get(this);

                int position = i;
                button.setOnAction(event -> displayPreparing(position));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayPreparing(int position) {
        playMediaSwapCamera();

        Camera camera = cameras.get(position-1);

        if (camera.isDarknessStatus()) {
            cameraImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/dark.png".formatted(camera.getSourcePath())))
            ));
        }
        else {
            String fileName = StringCreator.createPathImage(GameEngineInstance.getGameEngine().getAttackEntities().values().stream().toList(), position);

            cameraImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/%s.png".formatted(camera.getSourcePath(), fileName)))
            ));
        }

        soundButton.setDisable(!cameras.get(position-1).isSoundPlayAbilityStatus());

        currentViewPosition = position;
    }

    public void closeCameras(ActionEvent event) {
        playMediaCloseCameras();

        LoadersUtil.loadOffice(currentViewPosition);
    }

    public void playSound() {
        Camera currentCamera = cameras.get(currentViewPosition-1);

        playMediaSoundOnCamera();

        currentCamera.playSound();

        soundButton.setDisable(true);
    }

    private void playMediaSoundOnCamera() {
        int num = random.nextInt(3);

        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() {
                MediaPlayer player;
                switch (num) {
                    case 0:
                        player = new MediaPlayer(cameraHahaSound);
                        break;
                    case 1:
                        player = new MediaPlayer(cameraHiSound);
                        break;
                    case 2:
                        player = new MediaPlayer(cameraHelloSound);
                        break;
                    default:
                        return null;
                }
                player.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }

    private void playMediaSwapCamera() {
        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(cameraSwapSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }

    private void playMediaCloseCameras() {
        Task<Void> soundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(camerasCloseSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(soundTask).start();
    }
}
