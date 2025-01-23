package ru.itis.server;

import javafx.application.Platform;
import javafx.stage.Stage;
import ru.itis.nightindvoika.App;
import ru.itis.nightindvoika.mainClasses.GameData;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.jar.JarOutputStream;

public class Client {
//    private static final String SERVER_ADDRESS = "26.232.203.43";
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 443;
    private GameEngine gameEngine;

    public Client(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        System.out.println(gameEngine);
        start();
    }

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        App app = new App(gameEngine);

        new Thread(() -> {
            Platform.startup(() -> {
                try {
                    app.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();

        new Client(gameEngine);
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Отправка текущего состояния GameEngine
            new Thread(() -> {
                try {
                    while (socket.isConnected()) {

                        if (gameEngine.isUpdate()) { // если ничего нового, то и посылать серверу ничего не будем
                            synchronized (gameEngine) {
                                out.writeObject(gameEngine);
                                out.flush();
                                out.reset();
                                gameEngine.setUpdate(false);
                            }
                        }
                        Thread.sleep(1000); // интервал - секунда
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // прием data от сервера
            while (socket.isConnected()) {
                GameData data = (GameData) in.readObject();

                System.out.println("----------");
                System.out.println("Received GameData from server.");
                System.out.println("----------");

                gameEngine.updateFromGameData(data);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}