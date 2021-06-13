import RMI.RemoteOrder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ServerRmiTask6 {

    public static final String UNIQUE_BINDING_NAME = "server.task6";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException, SQLException {

        final RemoteOrder order = new RemoteOrder();

        final Registry registry = LocateRegistry.createRegistry(3789);

        Remote stub = UnicastRemoteObject.exportObject(order, 0);
        registry.bind(UNIQUE_BINDING_NAME, stub);
        System.out.println("Server started.");

        Thread.sleep(Integer.MAX_VALUE);

    }
}