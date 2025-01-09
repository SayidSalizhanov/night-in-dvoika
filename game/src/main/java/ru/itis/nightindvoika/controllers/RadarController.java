package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import ru.itis.nightindvoika.entites.AttackEntity;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;
import ru.itis.nightindvoika.util.LoadersUtil;
import ru.itis.nightindvoika.util.PositionOnFrame;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RadarController implements Initializable {

    private Map<String, AttackEntity> attackEntities;

    @FXML
    Circle witherSkeletonMark, skeletonMark, zombieMark, creeperMark;
    @FXML
    Button refreshRadar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        attackEntities = GameEngineInstance.getGameEngine().getAttackEntities();
    }

    public void display() {

        PositionOnFrame witherSkeletonPositionOnFrame = attackEntities.get("witherSkeleton").getCurrentPositionOnFrame();
        witherSkeletonMark.setLayoutX(witherSkeletonPositionOnFrame.getX());
        witherSkeletonMark.setLayoutY(witherSkeletonPositionOnFrame.getY());

        PositionOnFrame skeletonPositionOnFrame = attackEntities.get("skeleton").getCurrentPositionOnFrame();
        skeletonMark.setLayoutX(skeletonPositionOnFrame.getX());
        skeletonMark.setLayoutY(skeletonPositionOnFrame.getY());

        PositionOnFrame zombiePositionOnFrame = attackEntities.get("zombie").getCurrentPositionOnFrame();
        zombieMark.setLayoutX(zombiePositionOnFrame.getX());
        zombieMark.setLayoutY(zombiePositionOnFrame.getY());

        PositionOnFrame creeperPositionOnFrame = attackEntities.get("creeper").getCurrentPositionOnFrame();
        creeperMark.setLayoutX(creeperPositionOnFrame.getX());
        creeperMark.setLayoutY(creeperPositionOnFrame.getY());
    }

    public void refreshRadar(ActionEvent event) {
        display();
    }
}
