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
    public boolean createUser(User user) {
        Connection conn = null;
        boolean userCreated = false;
        User isExist = this.getUserByEmail(user.getEmail());
        if (isExist == null) {
            String query = "INSERT INTO User (name, email, address, phone, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";

            try {
                conn = AIMSDB.getConnection();
                System.out.println("Connected to the database");
                PreparedStatement stmt = conn.prepareStatement(query);
                System.out.println("Prepared statement created successfully");
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getAddress());
                stmt.setString(4, user.getPhone());
                stmt.setString(5, user.getPassword());
                stmt.setInt(6, user.getUserType());

                int rowsAffected = stmt.executeUpdate();
                System.out.println(">>>User creation check: " + rowsAffected);
                userCreated = rowsAffected > 0;

            } catch (SQLException e) {
                System.out.println("Error creating user");
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return userCreated;
        }
        return userCreated;
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

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET name = ?, email = ?, address = ?, phone = ?, user_type = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getPhone());
            statement.setInt(5, user.getUserType());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        }
    }

    public User getUserByEmail(String email){
        User user = null;
        Connection conn = null;
        System.out.println(">>>check email: " + email);
        String query = "SELECT * FROM User WHERE email = ?;";
        try{
            conn = AIMSDB.getConnection();
            System.out.println("ket not db");
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("goi db thanh cong");
            stmt.setString(1, email);
            ResultSet res = stmt.executeQuery();
            System.out.println(">>>>check get user by email");
            while (res.next()){
                user = new User();
                user.setId(res.getInt("id"));
                user.setName(res.getString("name"));
                user.setPassword(res.getString("password"));
                user.setUserType(res.getInt("user_type"));
                user.setEmail(res.getString("email"));
                user.setAddress(res.getString("address"));
                user.setPhone(res.getString("phone"));
                System.out.println(">>>check user: " + user.toString());
            }
        }catch (SQLException e){
            System.out.println("error user");
            e.printStackTrace();
        }

        return user;
    }
    public boolean registerUser(String name, String email, String address, String phone, String password, int user_type) throws SQLException {
        User user = new User(name, email, address, phone, password, user_type);
        return createUser(user);
    }


}
