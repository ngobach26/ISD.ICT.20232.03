package DAO;

import db.AIMSDB;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteOrderDAO implements OrderDAO{

    private final Connection connection;

    public SQLiteOrderDAO() {
        this.connection = AIMSDB.getConnection();
    }

    public int createOrder(DeliveryInformation deliveryInformation, Order order, User user) throws SQLException {
        String insertDeliverySQL = "INSERT INTO Delivery_information (userID, province_city, delivery_address, recipient_name, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        String insertOrderSQL = "INSERT INTO \"ORDER\" (total, total_shipping_fee, deliveryID) VALUES (?, ?, ?)";

        try (PreparedStatement deliveryStatement = connection.prepareStatement(insertDeliverySQL, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement orderStatement = connection.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false); // Begin transaction

            // Insert delivery information
            deliveryStatement.setInt(1, user.getId());
            deliveryStatement.setString(2, deliveryInformation.getProvinceCity());
            deliveryStatement.setString(3, deliveryInformation.getDeliveryAddress());
            deliveryStatement.setString(4, deliveryInformation.getRecipientName());
            deliveryStatement.setString(5, deliveryInformation.getEmail());
            deliveryStatement.setString(6, deliveryInformation.getPhoneNumber());

            int deliveryRowsAffected = deliveryStatement.executeUpdate();
            if (deliveryRowsAffected == 0) {
                connection.rollback();
                throw new SQLException("Failed to insert delivery information!");
            }

            try (ResultSet generatedKeys = deliveryStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int deliveryID = generatedKeys.getInt(1);

                    // Insert order
                    double total = order.calculateTotalProductIncludeVAT();
                    double totalShippingFee = order.calculateTotalPrice(deliveryInformation);

                    orderStatement.setDouble(1, total);
                    orderStatement.setDouble(2, totalShippingFee);
                    orderStatement.setInt(3, deliveryID);

                    int orderRowsAffected = orderStatement.executeUpdate();
                    if (orderRowsAffected == 0) {
                        connection.rollback();
                        throw new SQLException("Failed to insert order!");
                    }

                    try (ResultSet orderGeneratedKeys = orderStatement.getGeneratedKeys()) {
                        if (orderGeneratedKeys.next()) {
                            int orderID = orderGeneratedKeys.getInt(1);

                            // Insert order media
                            for (OrderMedia orderMedia : order.getlstOrderMedia()) {
                                createOrderMedia(orderMedia, orderID);
                            }

                            connection.commit(); // Commit transaction
                            System.out.println("Order & OrderMedia added successfully!");
                            return orderID;
                        }
                    }
                }
            }

            connection.rollback();
            throw new SQLException("Failed to create order!");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void createOrderMedia(OrderMedia orderMedia, int orderId) throws SQLException {
        String sql = "INSERT INTO ORDER_MEDIA (mediaID, orderID, price, number_of_products) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderMedia.getMedia().getId());
            preparedStatement.setInt(2, orderId);
            preparedStatement.setDouble(3, orderMedia.getPrice());
            preparedStatement.setInt(4, orderMedia.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
