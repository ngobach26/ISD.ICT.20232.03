package DAO;

import db.AIMSDB;
import entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SQLiteUserDAO implements UserDAO {
    private static Logger LOGGER = Logger.getLogger(SQLiteUserDAO.class.getName());
    private Connection connection;

    public SQLiteUserDAO() throws SQLException {
        this.connection = AIMSDB.getConnection();
    }
    @Override
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO User (name, email, address, phone, user_type, password) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getAddress());
        statement.setString(4, user.getPhone());
        statement.setInt(5, user.getUserType());
        statement.setString(6, user.getPassword());
        statement.executeUpdate();
    }
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                int userType = resultSet.getInt("user_type");

                User user = new User(id, name, email, address, phone, userType);
                users.add(user);
            }
        }
        return users;
    }


}
