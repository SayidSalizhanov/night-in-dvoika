package ru.itis.server;

import ru.itis.nightindvoika.mainClasses.GameData;
import ru.itis.nightindvoika.mainClasses.GameEngine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server; // Ссылка на сервер
    private ObjectOutputStream out;
    private GameEngine engine;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                engine = (GameEngine) in.readObject();
                System.out.println("Received GameEngine from client.");

                synchronized (server.getGameData()) {
                    server.getGameData().updateFromGameEngine(engine);
                }

                server.broadcast(server.getGameData());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendGameData(GameData data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}