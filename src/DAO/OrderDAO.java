package DAO;

import entity.order.Order;
import entity.order.OrderMedia;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    int createOrder(Order order) throws SQLException;
    void createOrderMedia(OrderMedia orderMedia, int orderId) throws SQLException;
}
