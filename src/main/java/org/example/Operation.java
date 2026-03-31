package org.example;
import java.sql.Timestamp;

public class Operation {
    public long userId;
    public Long receiverId;
    public int type;
    public int amount;
    public Timestamp date;

    public Operation(long userId, Long receiverId, int type, int amount, Timestamp date) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }
}
