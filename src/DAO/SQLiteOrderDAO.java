package DAO;

import db.AIMSDB;
import entity.order.Order;
import entity.order.OrderMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteOrderDAO implements OrderDAO{
    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO `Order` (email, address, phone, userID, shipping_fee, state, province, time, "
                + "shipping_instruction, rush_shipping_instruction, is_rush_shipping) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String email = order.getDeliveryInfo().get("email");
        String address = order.getDeliveryInfo().get("address");
        String phone = order.getDeliveryInfo().get("phone");
        int userID = 1;
        double shippingFee = order.calculateShippingFees();
        int state = 0;
        String province = order.getDeliveryInfo().get("province");
        String time = order.getDeliveryInfo().get("time");
        int isRushShipping = 0;
        if (order.getDeliveryInfo().get("isRushShipping").equals("Yes")) {
            isRushShipping = 1;
        }
        String shippingInstruction = order.getDeliveryInfo().get("instructions");
        String rushShippingInstruction = order.getDeliveryInfo().get("rushShippingInstruction");
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Set values for parameters
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, phone);
        preparedStatement.setInt(4, userID);
        preparedStatement.setDouble(5, shippingFee);
        preparedStatement.setInt(6, state);
        preparedStatement.setString(7, province);
        preparedStatement.setString(8, time);
        preparedStatement.setString(9, shippingInstruction);
        preparedStatement.setString(10, rushShippingInstruction);
        preparedStatement.setInt(11, isRushShipping);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Order added successfully!");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderID = generatedKeys.getInt(1);

                    // Call createOrderMedia using orderID
                    for (OrderMedia orderMedia : order.getlstOrderMedia()) {
                        createOrderMedia(orderMedia, orderID);
                    }
                    System.out.println("Order & OrderMedia added successfully!");
                    return orderID;
                }
            }
        } else {
            System.out.println("Failed to create order!");
            return -1;
        }
        return -1;
    }
    public void createOrderMedia(OrderMedia orderMedia, int orderId) throws SQLException {
        try {
            String sql = "INSERT INTO OrderMedia (mediaID, orderID, price, quantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, orderMedia.getMedia().getId());
                preparedStatement.setInt(2, orderId);
                preparedStatement.setDouble(3, orderMedia.getPrice());
                preparedStatement.setInt(4, orderMedia.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
