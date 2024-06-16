package controller;

import DAO.UserDAO;
import common.exception.MediaNotAvailableException;
import entity.user.User;
import services.DAOService.DAOFactory;
import services.DAOService.MediaService;
import services.DAOService.UserService;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final UserDAO userService ;

    public AdminController()  {
        this.userService = DAOFactory.getUserDAO();
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
