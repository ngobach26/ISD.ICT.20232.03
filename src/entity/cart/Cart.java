package entity.cart;

import common.exception.MediaNotAvailableException;
import entity.media.Media;
import services.DAOService.CartService;
import services.DAOService.MediaService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int cartId;
    private int userId;
    private static Cart cartInstance;
    private final List<CartMedia> lstCartMedia;
    private final MediaService mediaService;

    public static Cart getCart(int userId) throws SQLException {
        if (cartInstance == null || cartInstance.getUserId() != userId) {
            cartInstance = new Cart(userId);
        }
        return cartInstance;
    }

    public Cart(int userId) throws SQLException {
        this.userId = userId;
        this.lstCartMedia = new ArrayList<>();
        this.mediaService = MediaService.getInstance();

        // Retrieve the cart ID from the database
        CartService cartService = CartService.getInstance();
        this.cartId = cartService.getCartId(userId);

        // Retrieve the cart media items from the database and populate lstCartMedia
        this.lstCartMedia.addAll(cartService.getCartMediaItems(this.cartId));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Cart() {
        lstCartMedia = new ArrayList<>();
        mediaService = MediaService.getInstance();
    }

    public int getCartId() {
        return cartId;
    }

    public void addCartMedia(CartMedia cm) {
        lstCartMedia.add(cm);
    }

    public void removeCartMedia(CartMedia cm) {
        lstCartMedia.remove(cm);
    }

    public List<CartMedia> getListMedia() {
        return lstCartMedia;
    }

    public void emptyCart() {
        lstCartMedia.clear();
    }

    public CartMedia checkMediaInCart(Media media) {
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) {
                return cartMedia;
            }
        }
        return null;
    }

    public int getTotalMedia() {
        int total = 0;
        for (CartMedia cm : lstCartMedia) {
            total += cm.getQuantity();
        }
        return total;
    }

    public void checkAvailabilityOfProduct() throws SQLException, MediaNotAvailableException {
        mediaService.checkAvailabilityOfProduct(lstCartMedia); // Pass the list of cart media to the service method
    }

    public int calSubtotal() {
        int total = 0;
        for (CartMedia cm : lstCartMedia) {
            total += cm.getPrice() * cm.getQuantity();
        }
        return total;
    }

}
