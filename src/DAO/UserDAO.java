package DAO;

import entity.media.Media;
import entity.user.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void createUser(User user) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
}
