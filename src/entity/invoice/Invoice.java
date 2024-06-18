package entity.invoice;

import entity.order.DeliveryInformation;
import entity.order.Order;

public class Invoice {

    private DeliveryInformation deliveryInformation;
    private Order order;
    private int amount;
    
    public Invoice(){

    }

    public Invoice(DeliveryInformation deliveryInformation, Order order){
        this.deliveryInformation = deliveryInformation;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public DeliveryInformation getDeliveryInformation() {
        return this.deliveryInformation;
    }
}
