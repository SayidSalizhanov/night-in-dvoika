package ru.itis.nightindvoika.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.players.Defender;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RadarForDefenderController implements Initializable {

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

    private PauseTransition pauseTransition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defender = GameEngineInstance.getGameEngine().getDefender();

        Map<String, AttackEntity> attackEntities = GameEngineInstance.getGameEngine().getAttackEntities();
        witherSkeleton = attackEntities.get("witherSkeleton");
        skeleton = attackEntities.get("skeleton");
        zombie = attackEntities.get("zombie");
        creeper = attackEntities.get("creeper");
    }

    public void display() {
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
        attackEntityMark.setLayoutY(position.getY());
    }

    public void backToOffice(ActionEvent event) {
        pauseTransition.stop();
        LoadersUtil.loadOffice(event);
    }

    private void startTimer() {
        pauseTransition = new PauseTransition(Duration.seconds(defender.getRadarVisibleInSeconds()));
        pauseTransition.setOnFinished(event -> {
            backButton.fire();
        });
        pauseTransition.play();
    }
}
