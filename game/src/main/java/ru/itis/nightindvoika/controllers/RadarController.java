package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RadarController implements Initializable {

    /*
    Данный контроллер будет основан на конкретных сущностях, а не на мапе сущностей,
    потому что изначально в этом нет смысла, так как кнопки созданы только для этих 4-ёх сущностей.
    Попытка реализации через мапу заставит использовать рефлексию, что гораздо муторнее,
    чем написать логика отдельно для каждой сущности из известных 4-ёх.

    SOLID, goodbye...
    */

    private AttackEntity witherSkeleton;
    private AttackEntity skeleton;
    private AttackEntity zombie;
    private AttackEntity creeper;

    private Attacker attacker;

    @FXML
    Circle witherSkeletonMark, skeletonMark, zombieMark, creeperMark;
    @FXML
    Button refreshRadar;
    @FXML
    Button witherSkeletonMoveForwardButton, skeletonMoveForwardButton, zombieMoveForwardButton, creeperMoveForwardButton;
    @FXML
    Button witherSkeletonMoveBackButton, skeletonMoveBackButton, zombieMoveBackButton, creeperMoveBackButton;
    @FXML
    Button menuButton;
    @FXML
    Button muteAllCamerasButton;
    @FXML
    Button breakAllCamerasButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, AttackEntity> attackEntities = GameEngineInstance.getGameEngine().getAttackEntities();
        witherSkeleton = attackEntities.get("witherSkeleton");
        skeleton = attackEntities.get("skeleton");
        zombie = attackEntities.get("zombie");
        creeper = attackEntities.get("creeper");

        attacker = GameEngineInstance.getGameEngine().getAttacker();

        setOnActionMoveButtons(witherSkeleton, witherSkeletonMoveForwardButton, witherSkeletonMoveBackButton);
        setOnActionMoveButtons(skeleton, skeletonMoveForwardButton, skeletonMoveBackButton);
        setOnActionMoveButtons(zombie, zombieMoveForwardButton, zombieMoveBackButton);
        setOnActionMoveButtons(creeper, creeperMoveForwardButton, creeperMoveBackButton);
    }

    public void display() {
        setButtonDisableOrAllowAll();
        setMuteAllCamerasButtonDisableOrAllow();
        setBreakAllCamerasButtonDisableOrAllow();
        setPositionOnFrameAll();
    }

    public void refreshRadar(ActionEvent event) {
        display();
    }

    public void moveForward(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        attackEntity.moveForward();

        moveForwardButton.setDisable(true);
        moveBackButton.setDisable(true);

        display();
    }

    public void moveBack(AttackEntity attackEntity, Button moveForwardButton, Button moveBackButton) {
        attackEntity.moveBack();

        moveForwardButton.setDisable(true);
        moveBackButton.setDisable(true);

        display();
    }

    public void backToMenu(ActionEvent event) {
        LoadersUtil.loadMainMenu(event);
    }

    public void muteAllCameras(ActionEvent event) {
        attacker.soundBreakAllCameras();
        muteAllCamerasButton.setDisable(true);
    }

    public void breakAllCameras(ActionEvent event) {
        attacker.setDarknessStatusOnCameras();
        breakAllCamerasButton.setDisable(true);
    }

    private void setMuteAllCamerasButtonDisableOrAllow() {
        muteAllCamerasButton.setDisable(!attacker.isSoundBreakAbilityStatus());
    }

    private void setBreakAllCamerasButtonDisableOrAllow() {
        breakAllCamerasButton.setDisable(!attacker.isSettingDarknessAbilityStatus());
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
