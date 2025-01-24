package ru.itis.server;

import javafx.application.Platform;
import javafx.stage.Stage;
import ru.itis.nightindvoika.App;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.mainClasses.GameEngine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "26.232.203.43";
//    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 443;
    private final GameEngine gameEngine;
    private final App app;

    public Client(GameEngine gameEngine, App app) {
        this.gameEngine = gameEngine;
        this.app = app;
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

        new Client(gameEngine, app);
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while (socket.isConnected()) {
                        if (!gameEngine.getActionQueue().isEmpty()) {
                            out.writeObject(gameEngine.getActionQueue().poll());
                            out.flush();
                            out.reset();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (socket.isConnected()) {
                Action action = (Action) in.readObject();

                System.out.println("----------");
                System.out.println("Received action from server.");
                System.out.println("----------");

                app.doSomeAction(action);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}