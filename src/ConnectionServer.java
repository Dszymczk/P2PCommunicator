import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionServer extends Thread {
    P2PCommunicator communicator;
    ServerSocket serverSocket;
    int port;
    List<ConnectionSession> connectionSessionList = new ArrayList<>();
    ServerWindow serverWindow;

    public ConnectionServer(P2PCommunicator communicator, int port) throws IOException {
        this.communicator = communicator;
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void run () {
        try {
            serverWindow = new ServerWindow(communicator);
            System.out.println("Server started");
            while (true) {
                handleNewMessage(serverSocket.accept());
            }
        } catch (IOException exception) {
            System.out.println("Error running server");
            System.exit(1);
        }
    }

    public void handleNewMessage(Socket newSocket) throws IOException {
        Message message = null;
        checkConnection(newSocket);
        DataInputStream dis = new DataInputStream(newSocket.getInputStream());
        String IPAddress = newSocket.getInetAddress().getHostAddress();

        try {
            message = Message.createMessageFromJsonString(dis.readUTF());
            message.setReceivedDateAsNow();
            message.setSenderAddress(IPAddress);
            for ( ConnectionSession session : connectionSessionList ) {
                if( session.getRecipientAddress().equals( IPAddress ) ) {
                    session.receiveMessage(message);
                }
            }
        } catch (Exception exception) {
            System.out.println("Failed to convert received message to Message Object - invalid input format");
            exception.printStackTrace();
        }

        System.out.println(message);
        dis.close();
        newSocket.close();
    }

    private boolean checkConnection(Socket socket) {
        String IPAddress = socket.getRemoteSocketAddress().toString();
        System.out.println("Message from: " + IPAddress);
        return true;
    }

    public void addSession(ConnectionSession newSession) {
        connectionSessionList.add(newSession);
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }
}
