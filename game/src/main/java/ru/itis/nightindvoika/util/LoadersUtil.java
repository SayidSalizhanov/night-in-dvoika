package ru.itis.nightindvoika.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.nightindvoika.controllers.CameraController;
import ru.itis.nightindvoika.controllers.OfficeController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadersUtil {
    public static final Map<String, FXMLLoader> loaders = new HashMap<>();
    public static final Map<String, Scene> scenes = new HashMap<>();

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
    }

    public static void loadOffice(ActionEvent event) {
        OfficeController officeController = loaders.get("office").getController();
        officeController.display();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = scenes.get("office");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadCamera(ActionEvent event) {
        CameraController cameraController = loaders.get("camera").getController();
        cameraController.display(1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = scenes.get("camera");
        stage.setScene(scene);
        stage.show();
    }
}
