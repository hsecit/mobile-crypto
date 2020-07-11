package tech.hsecit.project.crypto.blockchain;

public class TransactionBitData {
    String sender,recipient,amount;

    public TransactionBitData(String sender, String recipient, String amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getAmount() {
        return amount;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
