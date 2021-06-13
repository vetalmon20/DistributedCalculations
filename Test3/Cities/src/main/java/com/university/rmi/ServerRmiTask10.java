package com.university.rmi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerRmiTask10 {
    private final static Logger logger = LogManager.getLogger(ServerRmiTask10.class);
    public static final String UNIQUE_BINDING_NAME = "server.task10";
    public static final int PORT = 8998;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException, SQLException {
        final RemoteCommand command = new RemoteCommand();
        final Registry registry = LocateRegistry.createRegistry(PORT);

        Remote stub = UnicastRemoteObject.exportObject(command, 0);
        registry.bind(UNIQUE_BINDING_NAME, stub);
        logger.info("Server has been started");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
