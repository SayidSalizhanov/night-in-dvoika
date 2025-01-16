package ru.itis.nightindvoika.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import ru.itis.nightindvoika.util.LoadersUtil;

public class EndGameController {
    @FXML
    Button menuButton;
    @FXML
    Text infoText;

    private final Media defenderWinsSound = new Media(getClass().getResource("/static/sounds/nightEnd.mp3").toExternalForm());
    private final Media attackerWinsSound = new Media(getClass().getResource("/static/sounds/music-box.mp3").toExternalForm());
    private MediaPlayer mediaPlayer;

    public void displayPreparing(boolean winStatus) {
        if (winStatus) {
            infoText.setText("Defender wins. Attacker loses. Thanks for playing!!!");
            playMediaEndGame(defenderWinsSound);
        } else {
            infoText.setText("Defender loses. Attacker wins. Thanks for playing!!!");
            playMediaEndGame(attackerWinsSound);
        }
    }

    public void openMenu(ActionEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        LoadersUtil.loadStartGame();
    }

    private void playMediaEndGame(Media media) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }
}
