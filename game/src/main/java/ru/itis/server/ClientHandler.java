package ru.itis.server;

import lombok.Getter;
import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.main.StartGameAction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private ObjectOutputStream out;

    @Getter
    private UUID uuid; // нужен для того, чтобы не пересылать изменения клиенту, от которого эти изменения и пришли

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        uuid = UUID.randomUUID();
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                Action action = (Action) in.readObject();

                System.out.println("----------");
                System.out.println("Received action from client.");
                System.out.println("----------");

                if (action instanceof StartGameAction) {
                    server.handleStartGameAction();
                }
                else {
                    server.broadcast(action, uuid);
                }
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

    public void sendGameAction(Action action) {
        try {
            out.writeObject(action);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}