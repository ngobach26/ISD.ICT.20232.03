package DAO;

import entity.cart.Cart;
import entity.cart.CartMedia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CartDAO {
    void saveCart(Cart cart) throws SQLException;
    int getCartID(int userId);
    List<CartMedia> getCartMediaItems(int cartId);
}