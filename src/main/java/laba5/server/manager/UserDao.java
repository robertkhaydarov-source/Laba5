package laba5.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private final DatabaseHandler databaseHandler;

    public UserDao(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public boolean register(String username, String password) {
        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";
        try (Connection connect = databaseHandler.connect();
             PreparedStatement prSt = connect.prepareStatement(sql)) {

            prSt.setString(1, username);
            prSt.setString(2, password); // Сюда прилетает чистый хэш от клиента, пишем напрямую

            return prSt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка регистрации в БД: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticate(String username, String password) {
        // ИСПРАВЛЕНО: заменили password_hash на реальное имя колонки password
        String sql = "SELECT 1 FROM users WHERE login = ? AND password = ?";
        try (Connection conn = databaseHandler.connect();
             PreparedStatement prSt = conn.prepareStatement(sql)) {

            prSt.setString(1, username);
            prSt.setString(2, password);

            try (ResultSet rs = prSt.executeQuery()) {
                return rs.next(); // Если строка нашлась, вернет true
            }
        } catch (SQLException e) {
            log.error("Ошибка аутентификации в БД: " + e.getMessage());
            return false;
        }
    }
}