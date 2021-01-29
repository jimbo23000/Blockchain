public class MessageNode implements Message {
    private Comparable messageNodeMessage;

    private Message nextMessageNode;

    public MessageNode() {
        messageNodeMessage = null;
        nextMessageNode = null;
    }

    public MessageNode(Comparable message, Message next) {
        messageNodeMessage = message;
        nextMessageNode = next;
    }

    public Comparable getMessage() {
        return messageNodeMessage;
    }

    public Message getNext() {
        return nextMessageNode;
    }

    public void setMessage(Comparable message) {
        messageNodeMessage = message;
    }

    public void setNext(Message next) {
        nextMessageNode = next;
    }
}
