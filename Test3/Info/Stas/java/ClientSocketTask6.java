import service.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientSocketTask6 {

    public Scanner scanner = null;
    private String serverHost;
    private int serverPort;
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public ClientSocketTask6(String host, int port) throws IOException {
        serverHost = host;
        serverPort = port;

        System.out.println("Connecting to server...");

        clientSocket = new Socket(serverHost, serverPort);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        System.out.println("Connected.");
    }

    public void sendCommand(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ClientSocketTask6 socket = new ClientSocketTask6("localhost", 8080);
        socket.writeInfo();

        socket.scanner = new Scanner(System.in);
        while (true) {
            Message message = socket.readMessage(socket.scanner);

            if (message.getCommand() > 0 && message.getCommand() < 8) {
                socket.sendCommand(message);
            }

            try {
                System.out.println(socket.in.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (message.getCommand() == 7) {
                break;
            }
        }
    }

    public void writeInfo() {
        System.out.println("Hello, enter command [1..9] and maybe arguments:");
        System.out.println("1 + order(int) - command1");
        System.out.println("2 + sum(double) + count(int) - command2");
        System.out.println("3 + name(string) - command3");
        System.out.println("4 + name(string) - command4");
        System.out.println("5 - command5");
        System.out.println("6 + name(string) + count(int) - command6");
        System.out.println("7 - exit");
    }

    public Message readMessage(Scanner scanner) {
        Integer command = Integer.valueOf(scanner.next());
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
