package com.university.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTask10 {
    private final static Logger logger = LogManager.getLogger(ServerSocketTask10.class);
    private static int id = 1;
    private ServerSocket serverSocket = null;
    public final static int PORT = 8888;

    public ServerSocketTask10() {
        try {
            serverSocket = new ServerSocket(PORT);
            logger.info("Server side has been started");
        } catch (IOException e) {
            logger.warn(e);
        }
    }

    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                logger.info("Waiting for client call...");
                clientSocket = serverSocket.accept();
                logger.info("Client with id: " + id + " has been connected successfully");

                new ClientThread(clientSocket, id++);
            } catch (IOException e) {
                logger.warn(e);
                return;
            }
        }

    }

    public static void main(String[] args) {
        ServerSocketTask10 socket = new ServerSocketTask10();
        socket.run();
    }
}
