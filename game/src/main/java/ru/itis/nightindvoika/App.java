package ru.itis.nightindvoika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    public static final Map<String, FXMLLoader> loaders = new HashMap<>();
    public static final Map<String, Parent> roots = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        loadFxmlLoaders();
        GameEngineInstance.getGameEngine();

        Parent root = roots.get("mainMenu");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadFxmlLoaders() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/main_menu.fxml"));
        loaders.put("mainMenu", loader);
        roots.put("mainMenu", loader.load());

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/defender/office.fxml"));
        loaders.put("office", loader);
        roots.put("office", loader.load());

        loader = new FXMLLoader(getClass().getResource("/ru/itis/nightindvoika/defender/camera.fxml"));
        loaders.put("camera", loader);
        roots.put("camera", loader.load());
    }
}