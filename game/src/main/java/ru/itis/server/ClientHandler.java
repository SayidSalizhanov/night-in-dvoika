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

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                GameEngine engine = (GameEngine) in.readObject();

                System.out.println("----------");
                System.out.println("Received GameEngine from client.");
                System.out.println("----------");

                synchronized (server.getGameData()) {
                    server.getGameData().updateFromGameEngine(engine);
                }

                server.broadcast();
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
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}