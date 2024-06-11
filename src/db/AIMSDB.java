package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import utils.*;

public class AIMSDB {

	private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
	

	public static Connection getConnection() {
		Connection connect = null;
		if (connect != null)
			return connect;
		else {
			try {
	            Class.forName("org.sqlite.JDBC");
	            String url = Configs.DB_URL;
	            connect = DriverManager.getConnection(url);
				LOGGER.info("Connect database successfully");
			} catch (ClassNotFoundException e) {
				System.err.println("Không tìm thấy Driver JDBC cho SQLite. Đảm bảo rằng thư viện đã được thêm vào project.");
				e.printStackTrace();
			} 
			catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
			return connect;
		}
		
	}

//    public static void main(String[] args) {
//        AIMSDB.getConnection();
//    }
}
