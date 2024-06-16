package entity.user;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private int userType;
    private String password;
    private int status;

    public User(int id, String name, String email, String address, String phone, int userType){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
    }
    public User(int id, String name, String email, String address, String phone,int userType, String password, int status){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
        this.password = password;
        this.status = status;
    }
    public User(String name, String email, String address, String phone,int userType, String password){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
        this.password = password;
    }

    public User() {
    }
    public User(int id, String name, String phone, String address, String email) {
    	this.id = id;
    	this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
    }
	
	public User(String name, String email, String address, String phone, String password, int user_type) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.userType = user_type;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", password=" + password + ", user_type=" + userType + "]";
	}

}
