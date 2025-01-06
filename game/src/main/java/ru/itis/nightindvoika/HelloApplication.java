package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.players.Attacker;
import ru.itis.nightindvoika.players.Defender;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        GameEngine engine = new GameEngine();
        Attacker attacker = engine.getAttacker();
        Defender defender = engine.getDefender();

        defender.displayOffice();
    }

    public static void main(String[] args) {
        launch(args);
    }
}