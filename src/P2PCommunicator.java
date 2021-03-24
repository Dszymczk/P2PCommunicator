/**
 * Main class starting P2P communicator application
 * @author Damian Szymczyk
 * @version 1.0
 */
public class P2PCommunicator {
    StartWindow startWindow;
    ConnectionServer connectionServer;

    /**
     * Create new application. Opens start window
     * @see StartWindow
     */
    public P2PCommunicator() {
        startWindow = new StartWindow(this);
    }

    /**
     * Starts server
     * @param port Port that server listens on
     */
    public void startServer(int port) {
        try {
            connectionServer = new ConnectionServer(this, port);
            connectionServer.start();
            startWindow.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Open connection creation window
     */
    public void openNewConnectionWindow() {
        NewConnectionWindow connectionWindow = new NewConnectionWindow(this);
    }

    /**
     * Crate new connectionSesion
     * @param hostAddress Host server address
     * @param port Host server port
     */
    public void newConnection(String hostAddress, int port) {
        ConnectionSession connectionSession = new ConnectionSession(this.connectionServer, hostAddress, port);
    }

    public static void main(String[] args) {
        new P2PCommunicator();
    }
}
