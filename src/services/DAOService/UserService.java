package services.DAOService;

import DAO.MediaDAO;
import DAO.UserDAO;
import entity.order.Order;
import entity.user.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO;

    public UserService() throws SQLException {
        this.userDAO = DAOFactory.getUserDAO();
    }

    public static UserService getInstance() {
        try {
            // Check if an instance already exists, if not, create a new one
            if (instance == null) {
                instance = new UserService();
            }
        } catch (SQLException e) {
            // Handle the exception, log or throw a custom exception if needed
            // For example:
            e.printStackTrace();
        }
        return instance;
    }
    public void createUser(User user) throws SQLException {
        userDAO.createUser(user);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    public void updateUser(User user)throws SQLException{
        userDAO.updateUser(user);
    }
    public boolean registerUser(String name, String email, String address, String phone, String password, int userType) throws SQLException {
        return userDAO.registerUser(name, email, address, phone, password, userType);
    }

    public User getUserByEmail(String email){
        return userDAO.getUserByEmail(email);
    }

    public void deleteUser(int id) throws SQLException {
        userDAO.deleteUser(id);
    }


}
