public interface Message {
    Comparable getMessage();
    Message getNext();
    void setMessage(Comparable message);
    void setNext(Message next);
}
