package services.DAOService;

import DAO.CartDAO;
import entity.cart.Cart;
import entity.cart.CartMedia;

import java.sql.SQLException;
import java.util.List;

public class CartService {
    private static CartService instance;
    private final CartDAO cartDAO;
    public CartService() throws SQLException{
        this.cartDAO = DAOFactory.getCartDAO();
    }
    public static CartService getInstance()
    {
        try {
            if (instance == null) {
                instance = new CartService();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }
    public void saveCart(Cart cart) throws SQLException{
        cartDAO.saveCart(cart);
    }
    public int getCartId(int userId) throws SQLException{
        return cartDAO.getCartID(userId);
    }
    public List<CartMedia> getCartMediaItems(int cartId) throws SQLException {
        return cartDAO.getCartMediaItems(cartId);
    }
}
