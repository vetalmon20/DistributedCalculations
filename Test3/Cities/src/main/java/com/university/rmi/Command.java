package com.university.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Command extends Remote {
    String execute(String line) throws RemoteException;
}
