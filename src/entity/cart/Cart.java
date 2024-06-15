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
    private final List<CartMedia> lstCartMedia;
    private static Cart cartInstance;
    private final MediaService mediaService;

    public static Cart getCart() {
        if (cartInstance == null) cartInstance = new Cart();
        return cartInstance;
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

    public void addCartMedia(CartMedia cm) {
        lstCartMedia.add(cm);
    }

    public void removeCartMedia(CartMedia cm) {
        lstCartMedia.remove(cm);
    }

    public List<CartMedia> getListMedia() {
        return lstCartMedia;
    }

    // This function is the replacement for the clear cart function in payment class
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
