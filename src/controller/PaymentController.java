package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import DAO.IOrderDAO;
import entity.cart.Cart;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.user.User;
import services.DAOFactory;
import services.user.LoginManager;
import services.vnpay.IPaymentSubsystem;
import services.vnpay.PaymentSubsystem;
import services.vnpay.VNPaySubsystemController;



public class PaymentController extends BaseController {

    /**
     * Represent the Interbank subsystem
     */
    private final IPaymentSubsystem vnPay;
    private final IOrderDAO orderDAO;

    public PaymentController(){
        this.vnPay = new PaymentSubsystem(new VNPaySubsystemController());
        this.orderDAO = DAOFactory.getOrderDAO();
    }


    public PaymentTransaction makePayment(Map<String, String> res) throws IOException, SQLException {
        PaymentTransaction transaction = vnPay.getPaymentTransaction(res);
        try {
            PaymentTransaction.saveTransaction(transaction);
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return transaction;
    }

    public String generateURL(int amount, String content) throws IOException {
        return vnPay.generateURL(amount, content);
    }
    public int createOrder(DeliveryInformation deliveryInformation, Order order, User user) throws SQLException {
        return orderDAO.createOrder(deliveryInformation,order,user);
    }

    public void emptyCart(){
        User user = LoginManager.getSavedLoginInfo();
        Cart.getCart(user.getId()).emptyCart();
    }
}