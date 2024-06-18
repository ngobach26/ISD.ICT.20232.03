package utils;

import controller.PlaceOrderController;
import entity.order.Order;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class DeliveryValidateInfo {
    private static final Logger LOGGER = utils.LOGGER.getLogger(PlaceOrderController.class.getName());

    public static void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    public static String validateDeliveryInfo(HashMap<String, String> info) {
        if (!validateName(info.get("name"))) {
            return "Invalid name";
        }
        if (!validatePhoneNumber(info.get("phone"))) {
            return "Invalid phone number";
        }
        if (!validateAddress(info.get("address"))) {
            return "Invalid address";
        }
        if (!validateEmail(info.get("email"))) {
            return "Invalid email";
        }

        String province = info.get("province");
        if (province == null || province.isEmpty()) {
            return "Empty province";
        }

        if ("Yes".equals(info.get("isRushShipping"))) {
            if (!validateTime(info.get("time"))) {
                return "Invalid time";
            }
            String lowerCaseProvince = province.toLowerCase();
            if (!lowerCaseProvince.contains("hà nội") && !lowerCaseProvince.contains("hồ chí minh")) {
                return "Address not support rush shipping";
            }
        }
        return "Valid";
    }

    public static boolean validateTime(String time) {
        // Assuming time is in HH:mm format
        String timeRegex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        return time.matches(timeRegex);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        // Assuming a valid phone number has 10 digits
        String phoneRegex = "^\\d{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    public static boolean validateName(String name) {
        // Assuming a valid name contains only letters and spaces
        String nameRegex = "^[a-zA-Z\\s]+$";
        return name.matches(nameRegex);
    }

    public static boolean validateAddress(String address) {
        // Assuming a valid address can contain letters, numbers, spaces, and commas
        String addressRegex = "^[a-zA-Z0-9\\s,]+$";
        return address.matches(addressRegex);
    }

    public static boolean validateEmail(String email) {
        // Basic email validation regex
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }


    public static int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
