package services.mediaservice;

import DAO.MediaDAO;
import DAO.SQLiteMediaDAO;

import java.sql.SQLException;

public class MediaDAOFactory {
    public static MediaDAO getMediaDAO() throws SQLException {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteMediaDAO();
    }
}

