package service;

import DAO.DAOtask6;

import java.sql.SQLException;

public class CommandService {
    private DAOtask6 dao;

    public CommandService() throws SQLException {
        //dao = new DAOtask6();
    }

    public String doCommand(Message msg) {
        Integer command = msg.getCommand();

        if (command == 1) {
            //return dao.request1(command);
            return "command 1";
        }
        if (command == 2) {
            //return dao.request2(msg.getSumm(), msg.getCount());
            return "command 2";
        }
        if (command == 3) {
            //return dao.request3(msg.getName());
            return "command 3";
        }
        if (command == 4) {
            //return dao.request4(msg.getName());
            return "command 4";
        }
        if (command == 5) {
            //dao.request5();
            return "Command 5 finished.";
        }
        if (command == 6) {
            //dao.request6(msg.getName(), msg.getCount());
            return "Command 6 finished.";
        }
        return "Error command.";
    }
}
