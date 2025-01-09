package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RadarController implements Initializable {

    private AttackEntity witherSkeleton;
    private AttackEntity skeleton;
    private AttackEntity zombie;
    private AttackEntity creeper;

    @FXML
    Circle witherSkeletonMark, skeletonMark, zombieMark, creeperMark;
    @FXML
    Button refreshRadar;
    @FXML
    Button witherSkeletonMoveForwardButton, skeletonMoveForwardButton, zombieMoveForwardButton, creeperMoveForwardButton;
    @FXML
    Button witherSkeletonMoveBackButton, skeletonMoveBackButton, zombieMoveBackButton, creeperMoveBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, AttackEntity> attackEntities = GameEngineInstance.getGameEngine().getAttackEntities();
        witherSkeleton = attackEntities.get("witherSkeleton");
        skeleton = attackEntities.get("skeleton");
        zombie = attackEntities.get("zombie");
        creeper = attackEntities.get("creeper");

        setOnActionMoveButtons(witherSkeleton, witherSkeletonMoveForwardButton, witherSkeletonMoveBackButton);
        setOnActionMoveButtons(skeleton, skeletonMoveForwardButton, skeletonMoveBackButton);
        setOnActionMoveButtons(zombie, zombieMoveForwardButton, zombieMoveBackButton);
        setOnActionMoveButtons(creeper, creeperMoveForwardButton, creeperMoveBackButton);
    }

    public void display() {
        setButtonDisableOrAllowAll();
        setPositionOnFrameAll();
    }

    public void refreshRadar(ActionEvent event) {
        display();
    }

    public void moveForward(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        System.out.println(attackEntity.isMoveAbilityStatus());

        attackEntity.moveForward();

        System.out.println(attackEntity.isMoveAbilityStatus());

        moveForwardButton.setDisable(true);
        moveBackButton.setDisable(true);
    }

    public void moveBack(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        attackEntity.moveBack();
        moveForwardButton.setDisable(true);
        moveBackButton.setDisable(true);
    }

    private void setButtonDisableOrAllowAll() {
        setButtonsDisableOrAllow(witherSkeleton, witherSkeletonMoveForwardButton, witherSkeletonMoveBackButton);
        setButtonsDisableOrAllow(skeleton, skeletonMoveForwardButton, skeletonMoveBackButton);
        setButtonsDisableOrAllow(zombie, zombieMoveForwardButton, zombieMoveBackButton);
        setButtonsDisableOrAllow(creeper, creeperMoveForwardButton, creeperMoveBackButton);
    }

    private void setButtonsDisableOrAllow(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        if (attackEntity.isMoveAbilityStatus()) {
            moveForwardButton.setDisable(false);
            moveBackButton.setDisable(false);
        } else {
            moveForwardButton.setDisable(true);
            moveBackButton.setDisable(true);
        }
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

    private void setOnActionMoveButtons(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        moveForwardButton.setOnAction(event -> moveForward(attackEntity, moveForwardButton, moveBackButton));
        moveBackButton.setOnAction(event -> moveBack(attackEntity, moveForwardButton, moveBackButton));
    }
}
