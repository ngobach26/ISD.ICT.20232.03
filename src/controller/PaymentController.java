package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import entity.cart.Cart;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.user.User;
import services.DAOService.OrderService;
import services.vnpay.IPaymentSubsystem;
import services.vnpay.PaymentSubsystem;
import services.vnpay.VNPaySubsystemController;



public class PaymentController extends BaseController {

    /**
     * Represent the Interbank subsystem
     */
    private final IPaymentSubsystem vnPay;
    private final OrderService orderService;

    public PaymentController(){
        this.vnPay = new PaymentSubsystem(new VNPaySubsystemController());
        this.orderService = OrderService.getInstance();
    }


    public PaymentTransaction makePayment(Map<String, String> res) throws IOException, SQLException {
        PaymentTransaction transaction = vnPay.getPaymentTransaction(res);
        PaymentTransaction.saveTransaction(transaction);
        return transaction;
    }

    public String generateURL(int amount, String content) throws IOException {
        return vnPay.generateURL(amount, content);
    }
    public int createOrder(DeliveryInformation deliveryInformation, Order order, User user) throws SQLException {
        return orderService.createOrder(deliveryInformation,order,user);
    }

    public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}