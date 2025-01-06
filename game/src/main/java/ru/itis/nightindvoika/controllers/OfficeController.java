package ru.itis.nightindvoika.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;
import ru.itis.nightindvoika.entites.Office;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.util.StringCreator;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Data
public class OfficeController implements Initializable {

    private Office office;
    private boolean holdMaskStatus;

    @FXML
    ImageView backgroundImageView;
    @FXML
    Button putOnMaskButton;

    public OfficeController() {
        holdMaskStatus = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        office = GameEngine.getInstance().getOffice();
        display();
    }

    public void display() {

        String fileName = StringCreator.createPathImage(office.getAttackEntities(), office.getPosition());

        if (holdMaskStatus) {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/mask/%s.png".formatted(getOffice().getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Снять маску");
        }
        else {
            backgroundImageView.setImage(new Image(
                    Objects.requireNonNull(getClass().getResourceAsStream("%s/nomask/%s.png".formatted(getOffice().getSourcePath(), fileName)))
            ));

            putOnMaskButton.setText("Надеть маску");
        }
    }

    public void switchMaskMode(ActionEvent event) {
        holdMaskStatus = !holdMaskStatus;
        display();
    }
}
