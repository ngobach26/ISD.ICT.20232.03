package controller;

import common.exception.MediaNotAvailableException;
import entity.user.User;
import services.DAOService.MediaService;
import services.DAOService.UserService;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final UserService userService ;

    public AdminController() throws MediaNotAvailableException {
        this.userService = UserService.getInstance();
    }

    public void createUser(User user) throws SQLException{
        userService.createUser(user);
    }

    public List<User> getAllUsers() throws SQLException{
        return userService.getAllUsers();
    }

    public void updateUser(User user) throws SQLException{
        userService.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException{
        userService.deleteUser(id);
    }

}
