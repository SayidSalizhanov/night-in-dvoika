package ru.itis.server;

import ru.itis.nightindvoika.action.Action;
import ru.itis.nightindvoika.action.main.StartGameAction;
import ru.itis.nightindvoika.action.main.StartGameAvailableAction;
import ru.itis.nightindvoika.action.main.StartGameNotAvailableAction;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Server {
    private static final int PORT = 443;
    private static final String HOST = "localhost";
//    private static final String HOST = "26.232.203.43";
    private final List<ClientHandler> clients = new ArrayList<>();
    private int countOfReadyPlayers;
    private boolean gameStarted = false;

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

//                checkStartGameAvailable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public synchronized void checkStartGameAvailable() {
//        if (clients.size() >= 2) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000); // Задержка в 2 секунды, чтобы все стримы у новых клиентов успели инициализироваться
//                    broadcastStartGameAvailable();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//    }

    public synchronized void broadcast(Action action, UUID uuid) {
        for (ClientHandler client : clients) {
            if (!client.getUuid().equals(uuid))
                client.sendGameAction(action);
        }
    }

//    public synchronized void broadcastStartGameAvailable() {
//        for (ClientHandler client : clients) {
//            client.sendGameAction(new StartGameAvailableAction());
//        }
//    }

//    public synchronized void disconnectClient(ClientHandler clientHandler) {
//        clients.remove(clientHandler);
//
//        if (clients.size() < 2) {
//            clients.getFirst().sendGameAction(new StartGameNotAvailableAction());
//        }
//    }

    public synchronized void handleStartGameAction() {
        countOfReadyPlayers++;

        if (countOfReadyPlayers == clients.size() && !gameStarted) {
            gameStarted = true;

            // таймер - начало игры
            new Thread(() -> {
                try {
                    Thread.sleep(6000);
                    for (ClientHandler client : clients) {
                        client.sendGameAction(new StartGameAction());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            countOfReadyPlayers = 0;
            gameStarted = false;
        }
    }
}
