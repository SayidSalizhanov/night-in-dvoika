package ru.itis.nightindvoika.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import ru.itis.nightindvoika.controllers.CameraController;
import ru.itis.nightindvoika.controllers.OfficeController;
import ru.itis.nightindvoika.controllers.RadarController;
import ru.itis.nightindvoika.controllers.RadarForDefenderController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadersUtil {
    public static final Map<String, FXMLLoader> loaders = new HashMap<>();
    public static final Map<String, Scene> scenes = new HashMap<>();
    @Setter
    public static Stage primaryStage;

    public static void loadFxmlLoaders() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/nightindvoika/main_menu.fxml"));
        loaders.put("mainMenu", loader);
        scenes.put("mainMenu", new Scene(loader.load()));

        loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/nightindvoika/defender/office.fxml"));
        loaders.put("office", loader);
        scenes.put("office", new Scene(loader.load()));

        loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/nightindvoika/defender/camera.fxml"));
        loaders.put("camera", loader);
        scenes.put("camera", new Scene(loader.load()));

        loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/nightindvoika/attacker/radar.fxml"));
        loaders.put("radar", loader);
        scenes.put("radar", new Scene(loader.load()));

        loader = new FXMLLoader(LoadersUtil.class.getResource("/ru/itis/nightindvoika/defender/radar.fxml"));
        loaders.put("defenderRadar", loader);
        scenes.put("defenderRadar", new Scene(loader.load()));
    }

    public static void loadMainMenu() {
        Scene scene = scenes.get("mainMenu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadRadarForDefender() {
        RadarForDefenderController radarForDefenderController = loaders.get("defenderRadar").getController();
        radarForDefenderController.displayPreparing();

        Scene scene = scenes.get("defenderRadar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadOffice() {
        loadOffice(1);
    }

    public static void loadOffice(int nextCameraViewPosition) {
        OfficeController officeController = loaders.get("office").getController();
        officeController.setNextCameraViewPosition(nextCameraViewPosition);
        officeController.displayPreparing();

        Scene scene = scenes.get("office");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadCamera(int nextCameraViewPosition) {
        CameraController cameraController = loaders.get("camera").getController();
        cameraController.displayPreparing(nextCameraViewPosition);

        Scene scene = scenes.get("camera");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadRadar() {
        RadarController radarController = loaders.get("radar").getController();
        radarController.displayPreparing();

        Scene scene = scenes.get("radar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
