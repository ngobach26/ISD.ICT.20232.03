package controller;

import DAO.UserDAO;
import common.exception.LoginAccountException;
import common.exception.MediaNotAvailableException;
import common.exception.RegisterAccountException;
import entity.user.User;
import services.DAOService.UserService;

import java.sql.SQLException;

public class AuthController {

    private UserService userService ;

    public AuthController() throws MediaNotAvailableException {
        this.userService = UserService.getInstance();
    }
    public User validateLogin(String email, String password) throws LoginAccountException {

    	System.out.println("check user validate login");
        User accountDb = userService.getUserByEmail(email);
//        System.out.println(">>>check user: " + accountDb.toString());
        if (accountDb == null) {
            throw new LoginAccountException("Login fail - account or password is wrong");
        }

        if (!password.equals(accountDb.getPassword())) {
            throw new LoginAccountException("Login fail - account or password is wrong");
        }

        return accountDb;
    }

    public boolean registerUser(String name, String email, String address, String phone, String password, int user_type) throws LoginAccountException, SQLException {
        System.out.println("check user validate login");
        if(name == null && email == null && address == null && phone == null && password == null) {
            throw new RegisterAccountException("Please complete all information!");
        }
        boolean accountDb = userService.registerUser(name, email, address, phone, password, user_type);
        return accountDb;
    }

}
