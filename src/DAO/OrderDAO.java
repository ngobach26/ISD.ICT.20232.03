package DAO;

import entity.order.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    Order getOrderById(int id) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    List<Order> getOrdersByPage(int startRow, int pageSize, int state) throws SQLException;
    int createOrder(Order order) throws SQLException;
    void acceptOrderById(int orderId) throws SQLException;
    void declineOrderById(int orderId) throws SQLException;
    void confirmDeliveredOrderById(int orderId) throws SQLException;
    void deleteOrderById(int orderId) throws SQLException;
}
