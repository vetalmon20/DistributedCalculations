import service.CommandService;
import service.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThreads extends Thread {
    private CommandService service;
    private boolean isStopped = false;
    private int id;
    private Socket clientSocket = null;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientThreads(Socket socket, int id) throws SQLException {
        super();

        this.clientSocket = socket;
        this.id = id;
        service = new CommandService();

        this.start();
    }

    @Override
    public void run() {

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isStopped()) {
            try {
                Message msg = (Message) in.readObject();
                System.out.println("Received " + msg.getCommand() + " command from: " + id);

                if (msg.getCommand() != 7) {
                    out.writeObject(service.doCommand(msg));
                } else {
                    out.writeObject("Disconected from server.");
                    isStopped = true;
                }

                System.out.println("Send result to: " + id);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(
                        "Error accepting client connection.", e);
            }
        }

        System.out.println("Client " + id + " disconected.");
    }

    boolean isStopped() {
        return isStopped;
    }
}
