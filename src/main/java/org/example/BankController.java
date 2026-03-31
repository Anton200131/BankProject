package org.example;

import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public String putMoney(@RequestParam long id, @RequestParam int amount) {
        return db.processTransaction(id, 1, amount);
    }

    @PostMapping("/take")
    public String takeMoney(@RequestParam long id, @RequestParam int amount) {
        return db.processTransaction(id, 2, amount);
    }

    @GetMapping("/operations")
    public List<Operation> getOperations(
            @RequestParam long id,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        return db.getOperationListJson(id, from, to);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam long from, @RequestParam long to, @RequestParam int amount) {
        return db.transferMoney(from, to, amount);
    }

}
