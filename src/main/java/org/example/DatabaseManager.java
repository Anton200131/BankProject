package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final String url = System.getenv("DB_URL") != null
            ? System.getenv("DB_URL")
            : "jdbc:postgresql://localhost:5432/my_project";

    private final String user = System.getenv("DB_USER") != null
            ? System.getenv("DB_USER")
            : "postgres";

    private final String password = System.getenv("DB_PASSWORD") != null
            ? System.getenv("DB_PASSWORD")
            : "200131Ma&";
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public double getBalance(long userId) {
        String sql = "SELECT balance FROM users WHERE user_id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении баланса: " + e.getMessage());
        }
        return -1;
    }

    public String processTransaction(long userId, int type, int amount) {
        String updateBalanceSql = (type == 1)
                ? "UPDATE users SET balance = balance + ? WHERE user_id = ?"
                : "UPDATE users SET balance = balance - ? WHERE user_id = ?";
        String insertOpSql = "INSERT INTO operations (user_id, operation_type, amount) VALUES (?, ?, ?)";

        try (Connection conn = this.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt1 = conn.prepareStatement(updateBalanceSql);
                 PreparedStatement pstmt2 = conn.prepareStatement(insertOpSql)) {

                pstmt1.setInt(1, amount);
                pstmt1.setLong(2, userId);
                pstmt1.executeUpdate();

                pstmt2.setLong(1, userId);
                pstmt2.setInt(2, type);
                pstmt2.setInt(3, amount);
                pstmt2.executeUpdate();

                conn.commit();
                return "Операция успешно завершена";
            } catch (SQLException e) {
                conn.rollback();
                return "Ошибка транзакции: " + e.getMessage();
            }
        } catch (SQLException e) {
            return "Ошибка подключения: " + e.getMessage();
        }
    }

    public String transferMoney(long fromId, long toId, int amount) {
        if (fromId == toId) return "Ошибка: Нельзя перевести деньги самому себе";

        String withdrawSql = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
        String depositSql = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
        String historySql = "INSERT INTO operations (user_id, receiver_id, operation_type, amount) VALUES (?, ?, 3, ?)";

        try (Connection conn = this.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSql);
                 PreparedStatement depositStmt = conn.prepareStatement(depositSql);
                 PreparedStatement historyStmt = conn.prepareStatement(historySql)) {

                double senderBalance = getBalance(fromId);
                if (senderBalance < amount) {
                    conn.rollback();
                    return "Ошибка: Недостаточно средств у отправителя";
                }

                withdrawStmt.setInt(1, amount);
                withdrawStmt.setLong(2, fromId);
                withdrawStmt.executeUpdate();

                depositStmt.setInt(1, amount);
                depositStmt.setLong(2, toId);
                int rows = depositStmt.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return "Ошибка: Получатель не найден";
                }

                historyStmt.setLong(1, fromId);
                historyStmt.setLong(2, toId);
                historyStmt.setInt(3, amount);
                historyStmt.executeUpdate();

                conn.commit();
                return "Перевод выполнен успешно";

            } catch (SQLException e) {
                conn.rollback();
                return "Ошибка транзакции: " + e.getMessage();
            }
        } catch (SQLException e) {
            return "Ошибка подключения: " + e.getMessage();
        }
    }


    public List<Operation> getOperationListJson(long userId, String dateFrom, String dateTo) {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT * FROM operations WHERE user_id = ? " +
                "AND (CAST(? AS TIMESTAMP) IS NULL OR operation_date >= CAST(? AS TIMESTAMP)) " +
                "AND (CAST(? AS TIMESTAMP) IS NULL OR operation_date <= CAST(? AS TIMESTAMP)) " +
                "ORDER BY operation_date DESC";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            pstmt.setString(2, dateFrom);
            pstmt.setString(3, dateFrom);
            pstmt.setString(4, dateTo);
            pstmt.setString(5, dateTo);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long receiverId = rs.getObject("receiver_id") != null ? rs.getLong("receiver_id") : null;

                operations.add(new Operation(
                        rs.getLong("user_id"),
                        receiverId,
                        rs.getInt("operation_type"),
                        rs.getInt("amount"),
                        rs.getTimestamp("operation_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operations;
    }
}
