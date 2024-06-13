package services.DAOService;

import DAO.MediaDAO;
import DAO.OrderDAO;
import entity.order.DeliveryInformation;
import entity.order.Order;

import java.sql.SQLException;

public class OrderService {
    private static OrderService instance;
    private final OrderDAO orderDAO;

    public OrderService() throws SQLException {
        this.orderDAO = DAOFactory.getOrderDAO();
    }

    public static OrderService getInstance() {
        try {
            // Check if an instance already exists, if not, create a new one
            if (instance == null) {
                instance = new OrderService();
            }
        } catch (SQLException e) {
            // Handle the exception, log or throw a custom exception if needed
            // For example:
            e.printStackTrace();
        }
        return instance;
    }
    public int createOrder(DeliveryInformation deliveryInformation,Order order) throws SQLException {
        return orderDAO.createOrder(deliveryInformation,order);
    }

}
