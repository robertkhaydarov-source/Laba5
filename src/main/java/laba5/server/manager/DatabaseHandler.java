package laba5.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);
    private final String url;
    private final String username;
    private final String password;

    public DatabaseHandler(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection connect() {
        try{
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            DatabaseHandler.log.error(e.getMessage());
        }
        return null;
    }
}
