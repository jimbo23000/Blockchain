public class TransactionNode implements Transaction {
    private Comparable transactionNodeValue;

    private Transaction nextTransactionNode;

    public TransactionNode() {
        transactionNodeValue = null;
        nextTransactionNode = null;
    }

    public TransactionNode(Comparable value, Transaction next) {
        transactionNodeValue = value;
        nextTransactionNode = next;
    }

    public Comparable getValue() {
        return transactionNodeValue;
    }

    public Transaction getNext() {
        return nextTransactionNode;
    }

    public void setValue(Comparable value) {
        transactionNodeValue = value;
    }

    public void setNext(Transaction next) {
        nextTransactionNode = next;
    }
}
