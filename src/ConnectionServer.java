import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ConnectionServer creates new socket listening on port provided in port field and window representing this server.<br>
 * Class handles received messages and transer it to appropriate ConnectionSession from connectionSessionList.<br>
 * All received messages need to be in Message format.<br>
 * ConnectionServer is started as a new Thread
 * @see ServerWindow
 * @see Message
 * @author Damian Szymczyk
 * @version 1.0
 */
public class ConnectionServer extends Thread {
    /** Parent communicator*/
    P2PCommunicator communicator;
    /** Server socket */
    ServerSocket serverSocket;
    /** Port on which server listens */
    int port;
    /** List of added conetctions */
    List<ConnectionSession> connectionSessionList = new ArrayList<>();
    /** Associated serverWindow */
    ServerWindow serverWindow;

    /**
     * Create new instance of ConnectionServer
     * @param communicator parent communicator
     * @param port port to listen on
     */
    public ConnectionServer(P2PCommunicator communicator, int port) throws IOException {
        this.communicator = communicator;
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Starts server in a new thread with server window.<br>
     * Receives and handles messages
     */
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

    /**
     * Handle received message and transform it to Message object. Throws exception if message is in wrong format.<br>
     * Sets message date and sender address
     * @param newSocket Socket with new message
     */
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

    /**
     * Logs information about new messages
     * @param socket Socket with new message
     * @return
     */
    private void checkConnection(Socket socket) {
        String IPAddress = socket.getRemoteSocketAddress().toString();
        System.out.println("Message from: " + IPAddress);
    }

    /**
     * Adds new session to session list
     * @param newSession Session to add
     */
    public void addSession(ConnectionSession newSession) {
        connectionSessionList.add(newSession);
    }

    /**
     * Sets server socket
     * @param serverSocket Socket to set
     */
    private void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Sets server port
     * @param port Port to set
     */
    private void setPort(int port) {
        this.port = port;
    }

    /**
     * @return Server socket
     */
    private ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * @return Server port
     */
    public int getPort() {
        return port;
    }
}
