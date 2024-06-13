package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AIMSDB {

    private static final Logger LOGGER = Logger.getLogger(AIMSDB.class.getName());
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:assets/db/aims.db";
            connection = DriverManager.getConnection(url);
            LOGGER.log(Level.INFO, "Connected to database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to database: " + e.getMessage(), e);
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
        return connection;
    }
}
