public interface Transaction {
    Comparable getValue();
    Transaction getNext();
    void setValue(Comparable value);
    void setNext(Transaction next);
}
