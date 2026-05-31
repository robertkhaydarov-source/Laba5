package laba5.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private DatabaseHandler databaseHandler;
    public UserDao(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    public boolean register(String username, String password) {
        String sql = "INSERT INTO users (login, password_hash) Values (?, ?)";
        try (Connection connection = databaseHandler.connect();
            PreparedStatement prSt = connection.prepareStatement(sql)){
            prSt.setString(1, username);
            prSt.setString(2, password);
            prSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")){
                log.warn("Попытка регистрации дубликата логина: {}", username);

            }
            else{
                log.error(e.getMessage());
            }
            return false;
        }
    }
    public boolean authenticate(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password_hash = ?";
        try(Connection conn = databaseHandler.connect();
            PreparedStatement prSt = conn.prepareStatement(sql)){
            prSt.setString(1, username);
            prSt.setString(2, password);
            try (ResultSet rs= prSt.executeQuery()){
            return rs.next();}
        }catch(SQLException e){
            log.error(e.getMessage());
            return false;
        }
    }
}
