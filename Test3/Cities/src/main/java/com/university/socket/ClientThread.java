package com.university.socket;

import com.university.shell.Shell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private final static Logger logger = LogManager.getLogger(ClientThread.class);
    private boolean isStopped = false;
    private final int id;
    private final Socket clientSocket;
    private final Shell shell;

    public ClientThread(Socket socket, int id) {
        super();
        this.clientSocket = socket;
        this.id = id;
        this.shell = new Shell();
        this.start();
    }

    @Override
    public void run() {
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            logger.warn(e);
            return;
        }

        while (!isStopped) {
            try {
                String command = (String) in.readObject();
                logger.info("Received command");

                if (!"stop".equals(command)) {
                    out.writeObject(shell.execute(command));
                } else {
                    out.writeObject("Disconnected from server.");
                    isStopped = true;
                }

                logger.info("Send result to client with id:" + id);
            } catch (IOException | ClassNotFoundException e) {
                logger.warn("Error accepting client connection:" + e);
            }
        }
        logger.info("Client " + id + " disconected.");
    }
}
