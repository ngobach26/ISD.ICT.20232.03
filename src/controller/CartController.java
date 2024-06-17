package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import common.exception.MediaUpdateException;
import common.exception.ViewCartException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.user.User;
import utils.Utils;
import services.user.LoginManager;
/**
 * This class controls the flow of events when users view the Cart
 */
public class CartController extends BaseController{
    private static final Logger LOGGER = Utils.getLogger(CartController.class.getName());
    /**
     * This method checks the available products in Cart
     * @throws SQLException
     */
    public CartController(){
//        CartService cs = new CartService();
//        User user = LoginManager.getSavedLoginInfo();
//        cs.saveCart(Cart.getCart(user.getId()));
    }
    public void checkAvailabilityOfProduct() throws SQLException, MediaNotAvailableException {
        User user = LoginManager.getSavedLoginInfo();
        Cart.getCart(user.getId()).checkAvailabilityOfProduct();
    }

    /**
     * This method calculates the cart subtotal
     * @return subtotal
     */
    public int getCartSubtotal(){
        User user = LoginManager.getSavedLoginInfo();
        int subtotal = Cart.getCart(user.getId()).calSubtotal();
        return subtotal;
    }

    public void removeCartMedia(CartMedia cartMedia) throws SQLException, ViewCartException {
        User user = LoginManager.getSavedLoginInfo();
        Cart.getCart(user.getId()).removeCartMedia(cartMedia);
        LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
    }
    public void updateCartMediaQuantity(CartMedia cartMedia, int quantity) throws SQLException, MediaUpdateException {
        cartMedia.setQuantity(quantity);
        LOGGER.info("Updated quantity of " + cartMedia.getMedia().getTitle() + " to " + quantity);
    }

    public int checkStockAvailability(CartMedia cartMedia, int requestedQuantity) throws SQLException {
        int remainQuantity = cartMedia.getMedia().getQuantity();
        if (requestedQuantity > remainQuantity) {
            LOGGER.info("Product " + cartMedia.getMedia().getTitle() + " only remains " + remainQuantity + " (requested " + requestedQuantity + ")");
            return remainQuantity;
        }
        return requestedQuantity;
    }

    public List getListCartMedia() {
        User user = LoginManager.getSavedLoginInfo();
        return Cart.getCart(user.getId()).getListMedia();
    }
}