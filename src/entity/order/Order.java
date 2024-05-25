package entity.order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.db.AIMSDB;
import utils.Configs;

public class Order {

    private int id;
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    private OrderState state;

    public Order() {
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.shippingFees = orderDTO.getShipping_fee();
        this.deliveryInfo = new HashMap<>();
        this.lstOrderMedia = new ArrayList();
        deliveryInfo.put("email", orderDTO.getEmail());
        deliveryInfo.put("phone", orderDTO.getPhone());
        deliveryInfo.put("address", orderDTO.getAddress());
        deliveryInfo.put("province", orderDTO.getProvince());
        deliveryInfo.put("time", orderDTO.getTime());
        if (orderDTO.getIs_rush_shipping() == 1) {
            deliveryInfo.put("isRushShipping", "Yes");
        } else {
            deliveryInfo.put("isRushShipping", "No");
        }
        deliveryInfo.put("rushShippingInstruction", orderDTO.getRush_shipping_instruction());
        deliveryInfo.put("instructions", orderDTO.getShipping_instruction());
        switch (orderDTO.getState()) {
            case 0:
                this.state = OrderState.WAITING;
                break;
            case 1:
                this.state = OrderState.DELIVERING;
                break;
            case 2:
                this.state = OrderState.DELIVERED;
                break;
            case 3:
                this.state = OrderState.DECLINED;
                break;
        }
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

    public String getPhone() {
        return getDeliveryInfo().get("phone");
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
        // there should be a function to calculate the shipping fees
        // not place that function in the controller class
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap<String, String> getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public enum OrderState {
        WAITING,
        DELIVERING,
        DECLINED,
        DELIVERED
    }
}
