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
    private String password;
    private int user_type;
    
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
		this.user_type = user_type;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", password=" + password + ", user_type=" + user_type + "]";
	}

  
}
