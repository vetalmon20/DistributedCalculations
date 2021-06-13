package com.university.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketTask10 {
    private final static Logger logger = LogManager.getLogger(ClientSocketTask10.class);
    public static Scanner scanner = null;
    private String serverHost;
    private int serverPort;
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public ClientSocketTask10(String host, int port) throws IOException {
        serverHost = host;
        serverPort = port;
        scanner = new Scanner(System.in);
        logger.info("Connecting to the server...");
        clientSocket = new Socket(serverHost, serverPort);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        logger.info("Connection to the server has been successfully created");
    }

    public void sendCommand(String msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            logger.warn(e);
        }
    }


    public static void main(String[] args) throws IOException {
        ClientSocketTask10 socket = new ClientSocketTask10("localhost", ServerSocketTask10.PORT);
        writeInfo();

        while (true) {
            String message = scanner.nextLine();
            socket.sendCommand(message);
            try {
                String obj = (String) socket.in.readObject();
                logger.info(obj);
            } catch (ClassNotFoundException e) {
                logger.warn(e);
            }

            if("stop".equals(message)) {
                break;
            }
        }
    }

    public static void writeInfo() {
        String sb = "Hello. Possible commands:" + System.lineSeparator() +
                "'clang <city name> <language>' - see people in the city that speak input language" +
                System.lineSeparator() +
                "'lang <language>' - see cities where people speak that speak input language" +
                System.lineSeparator() +
                "'popul <population>' - see city info with input population" +
                System.lineSeparator() +
                "'old' - see the most oldest people in whole country" +
                System.lineSeparator() +
                "'stop' - exit" +
                System.lineSeparator();
        logger.info(sb);
    }
}
