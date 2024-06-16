package DAO;

import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.user.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO {
    int createOrder(DeliveryInformation deliveryInformation, Order order, User user) throws SQLException;

    void createOrderMedia(OrderMedia orderMedia, int orderId) throws SQLException;
}
