import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Represents connection session. Creates new window that represents connection.
 * @see NewConnectionWindow
 */
public class ConnectionSession {
    /** ConnectionServer associated with this session */
    ConnectionServer connectionServer;
    /** ConnectionFrame associated with this session */
    ConnectionFrame connectionFrame;
    /** Messages list */
    ArrayList<Message> messageList;
    /** Connection name */
    String connectionName;
    /** Recipient server address */
    String recipientAddress;
    /** Recipient server port */
    Integer recipientPort;

    /**
     * Create new ConnectionSession and creates link with provided Connection Server
     * @param connectionServer Associated connection server
     * @param recipientAddress Recipient server address
     * @param recipientPort Recipient server port
     */
    public ConnectionSession(ConnectionServer connectionServer, String recipientAddress, Integer recipientPort) {
        this.connectionFrame = new ConnectionFrame(this);
        this.connectionServer = connectionServer;
        connectionServer.addSession(this);
        messageList = new ArrayList<>();
        connectionName = "New connection: " + recipientAddress + ":" + recipientPort;
        this.recipientAddress = recipientAddress;
        this.recipientPort = recipientPort;
    }

    /**
     * Sends new message to recipient on recipientAddress:recipientPort. Sent message is enriched with sender and receiver address
     * @param content Message content
     */
    public void sendMessage(String content) {
        try {
            Socket socket = new Socket(recipientAddress, recipientPort);
            Message message = new Message(content);
            message.setReceiverAddress(socket.getInetAddress().getHostAddress());
            message.setSenderAddress("Me:localhost");
            messageList.add(message);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message.MessageAsJsonString());
            System.out.println("Sent: " + message.MessageAsJsonString());
            dos.close();
            socket.close();
        } catch( IOException exception) {
            System.out.println("Not able to connect to " + recipientAddress + " on port " + recipientPort);
            exception.printStackTrace();
            Message message = new Message("Not able to connect!!!!");
            message.setReceiverAddress("");
            message.setSenderAddress("LOG");
            messageList.add(message);
        }
    }

    /**
     * Add received message do messagesList and refresh messges text field in connectionFrame
     * @param message Received message
     */
    public void receiveMessage(Message message) {
        messageList.add(message);
        connectionFrame.refreshMessagesField();
    }

    /**
     * @return Messges list
     */
    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    /**
     * @return Recipient server address
     */
    public String getRecipientAddress() {
        return recipientAddress;
    }

    /**
     * Sets recipient server dddress
     * @param recipientAddress Recipient server address
     */
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    /**
     * @return Recipient server port
     */
    public Integer getRecipientPort() {
        return recipientPort;
    }

    /**
     * Sets recipient server port
     * @param recipientPort Recipient server port
     */
    public void setRecipientPort(Integer recipientPort) {
        this.recipientPort = recipientPort;
    }

    /**
     * Sets message list
     * @param messageList Message lsit
     */
    private void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    /**
     * Return connection name
     * @return Connection name
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Set connection name
     * @param connectionName connection name
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Not yet implemented
     * Returns last message
     * @return nothing
     */
    private String getLastMessage() {
        return "";
    }

    /**
     * Not yet implemented
     * Return last couple messages
     * @param amount numver of messages
     * @return messages list
     */
    private ArrayList<String> getLastMessages(int amount) {
        return  new ArrayList<>();
    }

    /**
     * Return messages in format appropriate for connectionFrame window to present to user.
     * @return Messages list
     */
    public String getContentOfMessageList() {
        String content = "";
        for ( Message message : messageList) {
            content += message.getContent();
            content += "\n";
        }
        return content;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        else if (object == null) return false;
        else if (object instanceof ConnectionSession) {
            Message messageObject = (Message) object;
            return messageObject.getReceiverAddress().equals(this.getRecipientAddress());
        }
        return false;
    }
}
