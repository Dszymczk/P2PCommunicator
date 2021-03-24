import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

/**
 * Class that represents message format and provide basic functions to handle those messages. <br>
 * Use JSON format to transfer messages
 * @author Damian Szymczyk
 * @version 1.0
 */
public class Message {
    String content;
    String senderAddress;
    String receiverAddress;
    Date receivedDate;
    // TODO - add source (sent/received)

    /**
     * Creates new message
     * @param content Message content
     */
    public Message(String content) {
        this.content = content;
        this.senderAddress="";
        this.receiverAddress="";
    }

    /**
     * Empty constructor
     */
    private Message() {}

    /**
     * Creates new message from JSON format
     * @param jsonMessage Message in JSON format
     * @return Message
     */
    static Message createMessageFromJsonString(String jsonMessage) throws IOException {
        ObjectMapper objectmapper = new ObjectMapper();
        return objectmapper.readValue(jsonMessage, Message.class);
    }

    /**
     * Set message content
     * @param content message content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return Message content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return Sender server address
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * Set sender server address
     * @param senderAddress Sender server address
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * @return Receiver address
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * Set receiver server address
     * @param receiverAddress Receiver server address
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    /**
     * @return Message arival date
     */
    public Date getReceivedDate() {
        return receivedDate;
    }

    /**
     * Set message arrival date
     * @param receivedDate Date
     */
    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * Set arival date as now
     */
    public void setReceivedDateAsNow() {
        this.receivedDate = new Date();
    }

    @Override
    public String toString() {
        return "Message [content="
                + content
                + ", senderAddress="
                + senderAddress
                + ", receiverAddress="
                + receiverAddress
                + ", sendDates="
                + ((receivedDate != null) ? receivedDate.toString() : "null")
                + "]";
    }

    /**
     * @return Message in JSON format
     */
    public String MessageAsJsonString() {
        String jsonStr = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonStr = objectMapper.writeValueAsString(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return jsonStr;
    }
}
