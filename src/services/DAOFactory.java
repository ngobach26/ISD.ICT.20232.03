package services;

import DAO.*;

public class DAOFactory {
    public static IMediaDAO getMediaDAO() {
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteMediaDAO();
    }
    public static IOrderDAO getOrderDAO(){
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteOrderDAO();
    }
    public static ICartDAO getCartDAO(){
        return new SQLiteCartDAO();
    }

    public static IUserDAO getUserDAO(){
        // Bạn có thể thay đổi implementation ở đây nếu muốn sử dụng loại cơ sở dữ liệu khác
        return new SQLiteUserDAO();
    }
}

