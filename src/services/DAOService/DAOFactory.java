package services.DAOService;

import DAO.*;

import java.sql.SQLException;

public class DAOFactory {
    public static MediaDAO getMediaDAO() throws SQLException {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteMediaDAO();
    }
    public static OrderDAO getOrderDAO() throws SQLException {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteOrderDAO();
    }
    public static CartDAO getCartDAO() throws SQLException {
        return new SQLiteCartDAO();
    }

    public static UserDAO getUserDAO() throws SQLException {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteUserDAO();
    }
}

