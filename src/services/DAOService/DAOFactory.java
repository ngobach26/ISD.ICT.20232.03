package services.DAOService;

import DAO.MediaDAO;
import DAO.OrderDAO;
import DAO.SQLiteMediaDAO;
import DAO.SQLiteOrderDAO;

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
}

