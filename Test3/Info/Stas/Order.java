package RMI;

import service.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Order extends Remote {
    public String doCommand(Message msg) throws RemoteException;
}
