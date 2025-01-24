package ru.itis.nightindvoika.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Data;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.players.Defender;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.util.Map;

@Data
public class RadarForDefenderController implements Controller {
    private GameEngine gameEngine;
    private LoadersUtil loadersUtil;

    private Defender defender;

    private AttackEntity witherSkeleton;
    private AttackEntity skeleton;
    private AttackEntity zombie;
    private AttackEntity creeper;

    @FXML
    Circle witherSkeletonMark, skeletonMark, zombieMark, creeperMark;
    @FXML
    Button backButton;
    @FXML
    Text radarTimeLimitText;

    private final Media closeRadarSound = new Media(getClass().getResource("/static/sounds/office/closeRadar.mp3").toExternalForm());

    private Timeline countdownTimeline;
    private int remainingSeconds;

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;

        defender = gameEngine.getDefender();

        Map<String, AttackEntity> attackEntities = gameEngine.getAttackEntities();

        witherSkeleton = attackEntities.get("witherSkeleton");
        witherSkeletonMark.setVisible(witherSkeleton.isRadarVisible());

        skeleton = attackEntities.get("skeleton");
        skeletonMark.setVisible(skeleton.isRadarVisible());

        zombie = attackEntities.get("zombie");
        zombieMark.setVisible(zombie.isRadarVisible());

        creeper = attackEntities.get("creeper");
        creeperMark.setVisible(creeper.isRadarVisible());
    }

    public void displayPreparing() {
        setPositionOnFrameAll();
        startTimer();
    }

    private void setPositionOnFrameAll() {
        setPositionOnFrame(witherSkeleton, witherSkeletonMark);
        setPositionOnFrame(skeleton, skeletonMark);
        setPositionOnFrame(zombie, zombieMark);
        setPositionOnFrame(creeper, creeperMark);
    }

    private void setPositionOnFrame(AttackEntity attackEntity, Circle attackEntityMark) {
        PositionOnFrame position = attackEntity.getCurrentPositionOnFrame();
        attackEntityMark.setLayoutX(position.getX());
        attackEntityMark.setLayoutY(position.getY() + 37);
    }

    public void backToOffice(ActionEvent event) {
        playMediaCloseRadar();
        if (countdownTimeline.getStatus() != Animation.Status.STOPPED) countdownTimeline.stop();
        loadersUtil.loadOffice();
    }

    private void startTimer() {
        remainingSeconds = defender.getRadarVisibleInSeconds();

        countdownTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                remainingSeconds--;
                radarTimeLimitText.setText(Integer.toString(remainingSeconds));

                if (remainingSeconds <= 0) {
                    countdownTimeline.stop();
                    backButton.fire();
                }
            })
        );

        countdownTimeline.setCycleCount(remainingSeconds);
        countdownTimeline.play();
    }

    private void playMediaCloseRadar() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MediaPlayer mediaPlayer = new MediaPlayer(closeRadarSound);
                mediaPlayer.play();
                return null;
            }
        };

        new Thread(task).start();
    }
}
