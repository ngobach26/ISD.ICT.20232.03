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
    private static final Logger LOGGER = Logger.getLogger(SQLiteUserDAO.class.getName());
    Connection connection;

    public SQLiteUserDAO() {
        this.connection = AIMSDB.getConnection();
    }

    @Override
    public boolean createUser(User user) {
        boolean userCreated = false;
        User isExist = getUserByEmail(user.getEmail());
        if (isExist == null) {
            String query = "INSERT INTO User (name, email, address, phone, password, user_type, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = null;
            try {
                stmt = connection.prepareStatement(query);
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getAddress());
                stmt.setString(4, user.getPhone());
                stmt.setString(5, user.getPassword());
                stmt.setInt(6, user.getUserType());
                stmt.setInt(7, 0);

                int rowsAffected = stmt.executeUpdate();
                LOGGER.info("User creation check: " + rowsAffected);
                userCreated = rowsAffected > 0;
            } catch (SQLException e) {
                LOGGER.severe("Error creating user: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException e) {
                    LOGGER.severe("Error closing resources: " + e.getMessage());
                }
            }
        }
        return userCreated;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                int userType = resultSet.getInt("user_type");
                String password = resultSet.getString("password");
                int status = resultSet.getInt("status");

                User user = new User(id, name, email, address, phone, userType, password,status);
                users.add(user);
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LOGGER.severe("Error closing resources: " + e.getMessage());
            }
        }
        return users;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ?, address = ?, phone = ?, user_type = ?, password = ?, status = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getPhone());
            statement.setInt(5, user.getUserType());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getStatus());
            statement.setInt(8, user.getId());

            statement.executeUpdate();
            LOGGER.info("User updated successfully: " + user.getId());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LOGGER.severe("Error closing resources: " + e.getMessage());
            }
        }
    }

    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM User WHERE email = ?";
        PreparedStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            res = stmt.executeQuery();
            while (res.next()) {
                user = new User();
                user.setId(res.getInt("id"));
                user.setName(res.getString("name"));
                user.setPassword(res.getString("password"));
                user.setUserType(res.getInt("user_type"));
                user.setEmail(res.getString("email"));
                user.setAddress(res.getString("address"));
                user.setPhone(res.getString("phone"));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving user by email: " + e.getMessage());
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                LOGGER.severe("Error closing resources: " + e.getMessage());
            }
        }
        return user;
    }

    public boolean registerUser(String name, String email, String address, String phone, String password, int user_type) {
        User user = new User(name, email, address, phone, password, user_type);
        return createUser(user);
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM User WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LOGGER.severe("Error closing resources: " + e.getMessage());
            }
        }
    }
}
