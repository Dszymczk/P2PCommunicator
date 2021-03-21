import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class Message {
    String content;
    String senderAddress;
    String receiverAddress;
    Date receivedDate;

    public Message(String content) {
        this.content = content;
        this.senderAddress="";
        this.receiverAddress="";
    }

    private Message() {}

    static Message createMessageFromJsonString(String jsonMessage) throws IOException {
        ObjectMapper objectmapper = new ObjectMapper();
        return objectmapper.readValue(jsonMessage, Message.class);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

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

    public static void main(String[] args) {
        Message message = new Message("Some content");
        message.setReceivedDateAsNow();
        System.out.println(message.toString());
        System.out.println(message.MessageAsJsonString());
        try {
            Message message2 = Message.createMessageFromJsonString(message.MessageAsJsonString());
            System.out.println(message2);
        } catch (IOException exceptin) {
            exceptin.printStackTrace();
        }
    }
}
