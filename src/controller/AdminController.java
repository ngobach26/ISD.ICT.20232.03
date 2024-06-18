package controller;

import DAO.UserDAO;
import entity.user.User;
import services.DAOFactory;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final UserDAO userDAO;

    public AdminController()  {
        this.userDAO = DAOFactory.getUserDAO();
    }

    public void createUser(User user) throws SQLException{
        userDAO.createUser(user);
    }

    public List<User> getAllUsers() throws SQLException{
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) throws SQLException{
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException{
        userDAO.deleteUser(id);
    }

}
