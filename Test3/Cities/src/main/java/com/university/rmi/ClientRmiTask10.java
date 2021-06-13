package com.university.rmi;

import com.university.socket.ClientSocketTask10;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientRmiTask10 {
    private final static Logger logger = LogManager.getLogger(ClientRmiTask10.class);
    public static final String UNIQUE_BINDING_NAME = "server.task10";

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        ClientSocketTask10.writeInfo();

        final Registry registry = LocateRegistry.getRegistry(ServerRmiTask10.PORT);
        Command command = (Command) registry.lookup(UNIQUE_BINDING_NAME);

        while (true) {
            String message = scanner.nextLine();
            logger.info(command.execute(message));

            if("stop".equals(message)) {
                break;
            }
        }
    }
}
