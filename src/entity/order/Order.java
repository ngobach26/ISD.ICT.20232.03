package entity.order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.AIMSDB;
import utils.Configs;

public class Order {

    private int id;

    private double total;
    private int shippingFees;

    private int deliveryID;
    private List lstOrderMedia;
    private OrderState state;

    public Order() {
        this.lstOrderMedia = new ArrayList<>();
    }

    public static ArrayList<Order> getOrdersByPage(int start, int pageSize, int state) {
        return null;
    }

    public static Order getOrderById(int id) {
        return null;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om) {
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om) {
        this.lstOrderMedia.remove(om);
    }

    public List<OrderMedia> getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public int getAmount() {
    	double amount = 0;
    	for (Object object : lstOrderMedia) {
    		OrderMedia om = (OrderMedia) object;
    		amount += om.getPrice();
    	}
    	return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }

    public int createOrder(DeliveryInformation deliveryInformation, Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = AIMSDB.getConnection();
            connection.setAutoCommit(false); // Begin transaction

            // Insert delivery information
            String insertDeliverySQL = "INSERT INTO Delivery_information (/* columns for delivery info */) VALUES (/* values for delivery info */)";
            preparedStatement = connection.prepareStatement(insertDeliverySQL, Statement.RETURN_GENERATED_KEYS);

            // Set the values for the delivery information here
            // preparedStatement.setXXX(index, value);

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
                preparedStatement = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);

                double total = order.calculateTotalProductIncludeVAT();
                double totalShippingFee = order.calculateShippingFees(deliveryInformation);
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
                        OrderMedia.createOrderMedia(orderMedia, orderID);
                    }

                    connection.commit(); // Commit transaction
                    System.out.println("Order added successfully!");
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


    public static void acceptOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 1
                + " WHERE id=" + orderId);
    }
    
    public static void declineOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 3
                + " WHERE id=" + orderId);
    }

    public static void confirmDeliveredOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 2
                + " WHERE id=" + orderId);
    }

    public static void deleteOrderById(int orderId) throws SQLException {
        String sql = "DELETE FROM `Order` WHERE id = ?";
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, orderId);
        preparedStatement.executeUpdate();
    }
    
    public String getStateString() {
        switch (getState()) {
            case WAITING:
                return "Pending approval";
            case DELIVERING:
                return "Delivering";
            case DECLINED:
                return "Refuse approval";
            case DELIVERED:
                return "Shipped successfully";
        }
        return "";
    }

    public int calculateShippingFees(DeliveryInformation deliveryInformation) {

        if (calculateTotalProductIncludeVAT() > 100000) {
            return 0;
        }

        double baseCost = 0;
        double additionalCostPerHalfKg = 0;

        if (deliveryInformation.isUrban()) {
            baseCost = 10000;
        } else {
            baseCost = 15000;
        }
        additionalCostPerHalfKg = 2500;

        double rushShippingCost = 0;

        if (deliveryInformation.isRushShipping()) rushShippingCost = 10000 * getNumberOfRushShippingProduct();

        double regularShippingCost = 0;

        regularShippingCost = baseCost;

        setShippingFees((int) (rushShippingCost + regularShippingCost));
        return (int) (rushShippingCost + regularShippingCost);
    }


    public int calculateTotalProductIncludeVAT() {
        double amount = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getMedia().getPrice() * om.getQuantity() ;
        }
        return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }

    public int calculateTotalProductNoVAT() {
        double amount = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getMedia().getPrice() * om.getQuantity() ;
        }
        return (int) (amount);
    }

    public int getNumberOfRushShippingProduct() {
        return getlstOrderMedia().size();
    }

    public double getMaxWeight() {
        float max = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            if (om.getMedia().getWeight() > max) max = om.getMedia().getWeight();
        }
        return max;
    }

    public int calculateTotalPrice(DeliveryInformation deliveryInformation) {
        return calculateTotalProductIncludeVAT() + calculateShippingFees(deliveryInformation);
    }


    public enum OrderState {
        WAITING,
        DELIVERING,
        DECLINED,
        DELIVERED
    }
}
