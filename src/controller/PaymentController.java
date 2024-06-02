package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import entity.cart.Cart;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import services.vnpay.IPaymentSubsystem;
import services.vnpay.PaymentSubsystem;
import services.vnpay.VNPaySubsystemController;


public class PaymentController extends BaseController {

    /**
     * Represent the Interbank subsystem
     */
    private IPaymentSubsystem vnPay;

    public PaymentController(){
        this.vnPay = new PaymentSubsystem(new VNPaySubsystemController());
    }


    public PaymentTransaction makePayment(Map<String, String> res) throws IOException, SQLException {
        PaymentTransaction transaction = vnPay.getPaymentTransaction(res);
        PaymentTransaction.saveTransaction(transaction);
        return transaction;
    }

    public String generateURL(int amount, String content) throws IOException {
        return vnPay.generateURL(amount, content);
    }
    public int createOrder(Order order) throws SQLException {
        return Order.createOrder(order);
    }

    public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}