package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import services.DAOService.MediaService;

public class PlaceOrderController extends BaseController{
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    private MediaService mediaService ;

    public PlaceOrderController() throws MediaNotAvailableException {
        this.mediaService = MediaService.getInstance();
    }


    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

     public Order createOrder() throws SQLException{
         Order order = new Order();
         for (Object object : Cart.getCart().getListMedia()) {
             CartMedia cartMedia = (CartMedia) object;
             OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                                                    cartMedia.getQuantity(),
                                                    cartMedia.getPrice());
             orderMedia.setPrice(orderMedia.calculatePrice());
             order.getlstOrderMedia().add(orderMedia);
         }
         return order;
     }

    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    public String validateDeliveryInfo(HashMap<String, String> info) {
        if(!validateName(info.get("name"))){
            return "Invalid name";
        }
        if(!validatePhoneNumber(info.get("phone"))){
            return "Invalid phone number";
        }
        if(!validateAddress(info.get("address"))){
            return "Invalid address";
        }
        if(!validateEmail(info.get("email"))){
            return "Invalid email";
        }

        if(info.get("province").isEmpty() || info.get("province") == null){
            return "Empty province";
        }

        if(info.get("isRushShipping").equals("Yes")){
            if(!validateTime(info.get("time"))){
                return "Invalid time";
            }
            String province = info.get("province").toLowerCase();
            if(!province.contains("hà nội") && !province.contains("hồ chí minh")) return "Address not support rush shipping";
        }
        return "Valid";
    }

    public boolean validateTime(String time) {
        // Assuming time is in HH:mm format
        String timeRegex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        return time.matches(timeRegex);
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        // Assuming a valid phone number has 10 digits
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    public boolean validateName(String name) {
        // Assuming a valid name contains only letters and spaces
        String nameRegex = "^[a-zA-Z\\s]+$";
        return name.matches(nameRegex);
    }

    public boolean validateAddress(String address) {
        // Assuming a valid address can contain letters, numbers, spaces, and commas
        String addressRegex = "^[a-zA-Z0-9\\s,]+$";
        return address.matches(addressRegex);
    }

    public boolean validateEmail(String email) {
        // Basic email validation regex
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }


    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
