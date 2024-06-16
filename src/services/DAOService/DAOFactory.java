package services.DAOService;

import DAO.*;

import java.sql.SQLException;

public class DAOFactory {
    public static MediaDAO getMediaDAO() {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteMediaDAO();
    }
    public static OrderDAO getOrderDAO(){
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteOrderDAO();
    }
    public static CartDAO getCartDAO(){
        return new SQLiteCartDAO();
    }

    public static UserDAO getUserDAO(){
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteUserDAO();
    }
}

