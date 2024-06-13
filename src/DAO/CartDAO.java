package DAO;

import entity.cart.Cart;

import java.sql.Connection;
import java.sql.SQLException;

public interface CartDAO {
    void saveCart(Cart cart) throws SQLException;
    int getCartID(int userId) throws SQLException;
}
