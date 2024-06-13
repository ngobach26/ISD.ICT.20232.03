package DAO;

import db.AIMSDB;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.order.OrderMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteOrderDAO implements OrderDAO{
    public int createOrder(DeliveryInformation deliveryInformation, Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = AIMSDB.getConnection();
            connection.setAutoCommit(false); // Begin transaction

            // Insert delivery information
            String insertDeliverySQL = "INSERT INTO Delivery_information (userID, province_city, delivery_address, recipient_name, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertDeliverySQL);

            preparedStatement.setInt(1, deliveryInformation.getUserID());
            preparedStatement.setString(2, deliveryInformation.getProvinceCity());
            preparedStatement.setString(3, deliveryInformation.getDeliveryAddress());
            preparedStatement.setString(4, deliveryInformation.getRecipientName());
            preparedStatement.setString(5, deliveryInformation.getEmail());
            preparedStatement.setString(6, deliveryInformation.getPhoneNumber());

            int deliveryRowsAffected = preparedStatement.executeUpdate();
            if (deliveryRowsAffected == 0) {
                connection.rollback();
                throw new SQLException("Failed to insert delivery information!");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int deliveryID = generatedKeys.getInt(1);

                // Insert order
                String insertOrderSQL = "INSERT INTO \"ORDER\" (total, total_shipping_fee, cartID, deliveryID) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(insertOrderSQL);

                double total = order.calculateTotalProductIncludeVAT();
                double totalShippingFee = order.calculateTotalPrice(deliveryInformation);
                int cartID = 1;

                preparedStatement.setDouble(1, total);
                preparedStatement.setDouble(2, totalShippingFee);
                preparedStatement.setInt(3, cartID);
                preparedStatement.setInt(4, deliveryID);

                int orderRowsAffected = preparedStatement.executeUpdate();
                if (orderRowsAffected == 0) {
                    connection.rollback();
                    throw new SQLException("Failed to insert order!");
                }

                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderID = generatedKeys.getInt(1);

                    // Insert order media
                    for (OrderMedia orderMedia : order.getlstOrderMedia()) {
                        createOrderMedia(orderMedia, orderID);
                    }

                    connection.commit(); // Commit transaction
                    System.out.println("Order & OrderMedia added successfully!");
                    return orderID;
                }
            }

            connection.rollback();
            throw new SQLException("Failed to create order!");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
