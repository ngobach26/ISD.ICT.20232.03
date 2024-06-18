package DAO;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

import java.util.List;

public interface ICartDAO {
    void saveCart(Cart cart);
    int getCartID(int userId);
    List<CartMedia> getCartMediaItems(int cartId);
    public void addMediaToCart(int userId, Media media, int quantity);
    public void removeMediaFromCart(int userId, int mediaId);
    public void deleteAllCartMediaForUser(int userId);
}