public class P2PCommunicator {
    StartWindow startWindow;
    ConnectionServer connectionServer;

    public P2PCommunicator() {
        startWindow = new StartWindow(this);
    }

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

    public void openNewConnectionWindow() {
        NewConnectionWindow newConnectionWindow = new NewConnectionWindow(this);
        //connectionServer.addSession();
    }

    public void newConnection(String hostAddress, int port) {
        ConnectionSession connectionSession = new ConnectionSession(this.connectionServer, hostAddress, port);
    }

    public static void main(String[] args) {
        new P2PCommunicator();
    }
}
