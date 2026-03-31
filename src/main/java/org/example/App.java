package org.example;

public class App {
    public static void main(String[] args) {

        DatabaseManager db = new DatabaseManager();
        long testId = 1;

        System.out.println("--- ПРОВЕРКА БАЛАНСА ---");

        double currentBalance = db.getBalance(testId);

        if (currentBalance == -1) {
            System.out.println("Ошибка: пользователь не найден или пароль неверный.");
        } else {
            System.out.println("Текущий баланс: " + currentBalance);

            db.putMoney(testId, 500.0);
            System.out.println("Баланс после +500: " + db.getBalance(testId));

            db.takeMoney(testId, 200.0);
            System.out.println("Итоговый баланс: " + db.getBalance(testId));
        }
        System.out.println("------------------------");
    }
}
