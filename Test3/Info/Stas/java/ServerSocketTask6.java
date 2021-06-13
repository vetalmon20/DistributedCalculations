import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerSocketTask6 {

    static int id = 1;

    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;

    public ServerSocketTask6(int port) {
        this.serverPort = port;
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Waiting for a client...");
                clientSocket = this.serverSocket.accept();
                System.out.println("Client " + id + " connected.");

                new ClientThreads(clientSocket, id++);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(
                        "Error accepting client connection.", e);
            }
        }

    }

    public static void main(String[] args) {
        ServerSocketTask6 socket = new ServerSocketTask6(8080);
        socket.run();
    }
}
