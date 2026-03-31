package org.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankController {

    private final DatabaseManager db = new DatabaseManager();

    @GetMapping("/balance/{id}")
    public String getBalance(@PathVariable long id) {
        double balance = db.getBalance(id);
        if (balance == -1) return "Пользователь не найден";
        return "Баланс пользователя " + id + ": " + balance;
    }

    @PostMapping("/put")
    public String putMoney(@RequestParam long id, @RequestParam double amount) {
        db.putMoney(id, amount);
        return "Счет пополнен успешно. Новый баланс: " + db.getBalance(id);
    }

    @PostMapping("/take")
    public String takeMoney(@RequestParam long id, @RequestParam double amount) {
        double currentBalance = db.getBalance(id);
        if (currentBalance < amount) {
            return "Ошибка: недостаточно средств. Текущий баланс: " + currentBalance;
        }

        db.takeMoney(id, amount);
        return "Деньги сняты успешно. Остаток: " + db.getBalance(id);
    }

}