package com.university.rmi;

import com.university.shell.Shell;

import java.rmi.RemoteException;

public class RemoteCommand implements Command{
    private final Shell shell;

    public RemoteCommand() {
        shell = new Shell();
    }

    @Override
    public String execute(String line) throws RemoteException {
        return shell.execute(line);
    }
}
