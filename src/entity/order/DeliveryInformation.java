package entity.order;

public class DeliveryInformation {

    private int deliveryID;
    private int userID;
    private String provinceCity;
    private String deliveryAddress;
    private String recipientName;
    private String email;
    private String phoneNumber;

    public String getIsRushShipping() {
        return isRushShipping;
    }

    public void setIsRushShipping(String isRushShipping) {
        this.isRushShipping = isRushShipping;
    }

    private String isRushShipping;
    private int supportForRushDelivery;

    public DeliveryInformation(int deliveryID, int userID, String provinceCity, String deliveryAddress, String recipientName, String email, String phoneNumber, String isRushShipping, int supportForRushDelivery) {
        this.deliveryID = deliveryID;
        this.userID = userID;
        this.provinceCity = provinceCity;
        this.deliveryAddress = deliveryAddress;
        this.recipientName = recipientName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isRushShipping = isRushShipping;
        this.supportForRushDelivery = supportForRushDelivery;
    }

    public DeliveryInformation() {

    }

    @Override
    public String toString() {
        return "{" +
                "  deliveryID='" + deliveryID + "'" +
                ", userID='" + userID + "'" +
                ", provinceCity='" + provinceCity + "'" +
                ", deliveryAddress='" + deliveryAddress + "'" +
                ", recipientName='" + recipientName + "'" +
                ", email='" + email + "'" +
                ", phoneNumber='" + phoneNumber + "'" +
                ", supportForRushDelivery='" + supportForRushDelivery + "'" +
                "}";
    }

    public int getDeliveryID() {
        return this.deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getProvinceCity() {
        return this.provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getRecipientName() {
        return this.recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSupportForRushDelivery() {
        return this.supportForRushDelivery;
    }

    public void setSupportForRushDelivery(int supportForRushDelivery) {
        this.supportForRushDelivery = supportForRushDelivery;
    }
    public boolean isRushShipping() {
        String isRushShipping = this.isRushShipping;
        return isRushShipping.equals("Yes");
    }

    public boolean isUrban() {
        String address = this.getProvinceCity();
        return address.toLowerCase().contains("hà nội");
    }
}
