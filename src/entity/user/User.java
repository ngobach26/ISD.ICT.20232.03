package entity.user;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

public class User {

    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private int userType;
    private String password;

    public User(int id, String name, String email, String address, String phone, int userType){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
    public User(String name, String email, String address, String phone,int userType, String password){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
        this.password = password;
    }

    
    // override toString method
    @Override
    public String toString() {
        return "{" +
            "  username='" + name + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            "}";
    }

    // getter and setter
    public String getName() {
        return this.name;
    }
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setusername(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
