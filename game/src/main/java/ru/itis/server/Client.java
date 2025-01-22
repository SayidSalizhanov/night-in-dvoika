package ru.itis.server;

import ru.itis.nightindvoika.App;
import ru.itis.nightindvoika.mainClasses.GameData;
import ru.itis.nightindvoika.mainClasses.GameEngine;
import ru.itis.nightindvoika.mainClasses.GameEngineInstance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private GameEngine gameEngine;

    public Client() {
        this.gameEngine = GameEngineInstance.getGameEngine();
        start();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            new Client();
        }).start();

        new Thread(() -> {
            App.main(new String[]{});
        }).start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Отправка текущего состояния GameEngine
            new Thread(() -> {
                try {
                    while (true) {
                        out.writeObject(gameEngine);
                        out.flush();
                        Thread.sleep(1000); // Отправляем данные каждые 1 секунду
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                GameData data = (GameData) in.readObject();
                System.out.println("Received GameData update from server.");
                gameEngine.updateFromGameData(data);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}