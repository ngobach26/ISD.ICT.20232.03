package DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.AIMSDB;
import entity.user.User;

public class UserDAO {
    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        Connection conn = null;
        String query = "SELECT * FROM User";

        try{
            conn = AIMSDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                User user = new User();
                user.setId(res.getInt("id"));
                user.setName(res.getString("name"));
                user.setPassword(res.getString("password"));
                user.setUser_type(res.getInt("user_type"));
                user.setEmail(res.getString("email"));
                user.setAddress(res.getString("address"));
                user.setPhone(res.getString("phone"));
                users.add(user);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
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
                user.setUser_type(res.getInt("user_type"));
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
    public boolean registerUser(String name, String email, String address, String phone, String password, int user_type) {
        User user = new User(name, email, address, phone, password, user_type);
        return createUser(user);
    }

    public boolean createUser(User user) {
        Connection conn = null;
        boolean userCreated = false;
        User isExist = this.getUserByEmail(user.getEmail());
        if(isExist == null) {
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
                 stmt.setInt(6, user.getUser_type());

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
}
