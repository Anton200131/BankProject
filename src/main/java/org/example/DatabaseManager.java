package org.example;

import java.sql.*;

public class DatabaseManager {

    private final String url = "jdbc:postgresql://localhost:5432/my_project";
    private final String user = "postgres";
    private final String password = "200131Ma&";
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

    public void putMoney(long userId, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
            System.out.println("Баланс пополнен на " + amount);
        } catch (SQLException e) {
            System.out.println("Ошибка при пополнении: " + e.getMessage());
        }
    }

    public void takeMoney(long userId, double amount) {
        String sql = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();
            System.out.println("Снято: " + amount);
        } catch (SQLException e) {
            System.out.println("Ошибка при снятии: " + e.getMessage());
        }
    }
}
