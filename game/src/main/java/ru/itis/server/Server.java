package ru.itis.server;

import lombok.Getter;
import ru.itis.nightindvoika.mainClasses.GameData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 443;
    private static final String HOST = "localhost";
//    private static final String HOST = "26.232.203.43";
    private final List<ClientHandler> clients = new ArrayList<>();
    @Getter
    private final GameData gameData = new GameData();

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        System.out.println("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName(HOST))) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcast() {
        for (ClientHandler client : clients) {
            client.sendGameData(gameData);
        }
    }
}
