package controller;

import DAO.UserDAO;
import common.exception.LoginAccountException;
import entity.user.User;

public class LoginController {
    public User validateLogin(String email, String password,  UserDAO userDAO) throws LoginAccountException {

    	System.out.println("check user validate login");
        User accountDb = userDAO.getUserByEmail(email);
//        System.out.println(">>>check user: " + accountDb.toString());
        if (accountDb == null) {
            throw new LoginAccountException("Login fail - account or password is wrong");
        }

        if (!password.equals(accountDb.getPassword())) {
            throw new LoginAccountException("Login fail - account or password is wrong");
        }

        return accountDb;
    }

}
