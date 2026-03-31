package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankTests {
    private final DatabaseManager db = new DatabaseManager();

    @Test
    public void testTransferLogic() {
        long senderId = 1;
        long receiverId = 2;
        int transferAmount = 100;

        double senderBalanceBefore = db.getBalance(senderId);
        double receiverBalanceBefore = db.getBalance(receiverId);

        String result = db.transferMoney(senderId, receiverId, transferAmount);

        assertEquals("Перевод выполнен успешно", result);

        assertEquals(senderBalanceBefore - transferAmount, db.getBalance(senderId),
                "Баланс отправителя должен уменьшиться на сумму перевода");

        assertEquals(receiverBalanceBefore + transferAmount, db.getBalance(receiverId),
                "Баланс получателя должен увеличиться на сумму перевода");
    }
}
