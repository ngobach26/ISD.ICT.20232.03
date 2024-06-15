package DAO;

import entity.user.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    boolean createUser(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void updateUser(User user) throws SQLException;
    boolean registerUser(String name, String email, String address, String phone, String password, int user_type) throws SQLException;
    User getUserByEmail(String email);
    void deleteUser(int userId) throws SQLException;
}
