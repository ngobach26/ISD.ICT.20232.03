package db;

import java.sql.DriverManager;
import java.util.logging.Logger;

import java.sql.Connection;

public class AIMSDB {
    private static final Logger LOGGER = utils.LOGGER.getLogger(Connection.class.getName());
    private static Connection connect;
    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:assets/db/AIMS.db";
            connect = DriverManager.getConnection(url);
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return connect;
    }


//    public static void main(String[] args) {
//        AIMSDB.getConnection();
//    }
}
