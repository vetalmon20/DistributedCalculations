package RMI;

import DAO.DAOtask6;
import service.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RemoteOrder implements Order {
    private DAOtask6 dao;

    public RemoteOrder() throws SQLException {
        dao = new DAOtask6();
    }

    @Override
    public String doCommand(Message msg) throws RemoteException {
        Integer command = msg.getCommand();

        if (command == 1) {
            return dao.request1(command);
        }
        if (command == 2) {
            return dao.request2(msg.getSumm(), msg.getCount());
        }
        if (command == 3) {
            return dao.request3(msg.getName());
        }
        if (command == 4) {
            return dao.request4(msg.getName());
        }
        if (command == 5) {
            dao.request5();
            return "Command 5 finished.";
        }
        if (command == 6) {
            dao.request6(msg.getName(), msg.getCount());
            return "Command 6 finished.";
        }
        return "Error command.";
    }
}
