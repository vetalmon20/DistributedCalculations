import RMI.Order;
import RMI.RemoteOrder;
import service.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientRmiTask6 {

    public static final String UNIQUE_BINDING_NAME = "server.task6";

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        writeInfo();

        final Registry registry = LocateRegistry.getRegistry(3789);
        Order order = (Order) registry.lookup(UNIQUE_BINDING_NAME);

        while (true) {
            Message message = readMessage(scanner);

            if (message.getCommand() > 0 && message.getCommand() < 8) {
                System.out.println(order.doCommand(message));
            }

            if (message.getCommand() == 7) {
                break;
            }
        }
    }

    public static void writeInfo() {
        System.out.println("Hello, enter command [1..9] and maybe arguments:");
        System.out.println("1 + order(int) - command1");
        System.out.println("2 + sum(double) + count(int) - command2");
        System.out.println("3 + name(string) - command3");
        System.out.println("4 + name(string) - command4");
        System.out.println("5 - command5");
        System.out.println("6 + name(string) + count(int) - command6");
        System.out.println("7 - exit");
    }


    public static Message readMessage(Scanner scanner) {
        int command = Integer.parseInt(scanner.next());
        Message msg = new Message(command);

        if (command == 1) {
            msg.setOrder(Integer.valueOf(scanner.next()));
        } else if (command == 2) {
            msg.setSumm(Double.valueOf(scanner.next()));
            msg.setCount(Integer.valueOf(scanner.next()));
        } else if (command == 3 || command == 4 || command == 6) {
            msg.setName(scanner.next());

            if (command == 6) {
                msg.setCount(Integer.valueOf(scanner.next()));
            }
        } else if (command != 7 && command != 5) {
            System.out.println("Error command");
        }

        return msg;
    }
}
