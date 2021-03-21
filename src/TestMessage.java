public class TestMessage {
    public static void main(String[] args) {
        ConnectionSession session = new ConnectionSession("localhost", 4441);
        session.sendMessage("Something");
    }
}
