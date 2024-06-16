package controller;

import DAO.UserDAO;
import entity.user.User;
import services.DAOFactory;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final UserDAO mediaDAO ;

    public AdminController()  {
        this.mediaDAO = DAOFactory.getUserDAO();
    }

    public void createUser(User user) throws SQLException{
        mediaDAO.createUser(user);
    }

    public List<User> getAllUsers() throws SQLException{
        return mediaDAO.getAllUsers();
    }

    public void updateUser(User user) throws SQLException{
        mediaDAO.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException{
        mediaDAO.deleteUser(id);
    }

}
