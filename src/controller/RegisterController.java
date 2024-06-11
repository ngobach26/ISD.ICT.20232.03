package controller;

import DAO.UserDAO;
import common.exception.LoginAccountException;
import common.exception.RegisterAccountException;
import entity.user.User;

public class RegisterController {
	 public boolean registerUser(String name, String email, String address, String phone, String password, int user_type, UserDAO userDAO) throws LoginAccountException {
	    	System.out.println("check user validate login");
	    	if(name == null && email == null && address == null && phone == null && password == null) {
	    		throw new RegisterAccountException("Please complete all information!");
	    	}
	        boolean accountDb = userDAO.registerUser(name, email, address, phone, password, user_type);
	        return accountDb;
	    }
}
