import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionSession {
    ArrayList<Message> messageList;
    String connectionName;
    String recipientAddress;
    Integer recipientPort;

    public ConnectionSession(String recipientAddress, Integer recipientPort) {
        messageList = new ArrayList<>();
        connectionName = "New connection: " + recipientAddress + ":" + recipientPort;
        this.recipientAddress = recipientAddress;
        this.recipientPort = recipientPort;
    }

    public void sendMessage(String content) {
        try {
            Socket socket = new Socket(recipientAddress, recipientPort);
            Message message = new Message(content);
            message.setReceiverAddress(socket.getInetAddress().getHostAddress());
            // TODO - change senderAddress
            message.setSenderAddress("Me:localhost");
            // TODO - add source (sent/received)
            messageList.add(message);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message.MessageAsJsonString());
            System.out.println("Sent: " + message.MessageAsJsonString());
            dos.close();
            socket.close();
        } catch( IOException exception) {
            System.out.println("Not able to connect to " + recipientAddress + " on port " + recipientPort);
            exception.printStackTrace();
        }
    }

    public void receiveMessage(Message message) {
        // TODO - change senderAddress
        message.setSenderAddress("Me:localhost");
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Integer getRecipientPort() {
        return recipientPort;
    }

    public void setRecipientPort(Integer recipientPort) {
        this.recipientPort = recipientPort;
    }

    private void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getLastMessage() {
        return "";
    }

    public ArrayList<String> getLastMessages(int amount) {
        return  new ArrayList<>();
    }

    public void writeMessagesToFile() {

    }

    public void loadMessagesFromFile() {

    }

    public void removeOldMessages() {

    }

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
