package entity.cart;

import DAO.MediaDAO;
import common.exception.MediaNotAvailableException;
import entity.media.Media;
import services.DAOFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int cartId;
    private int userId;
    private final List<CartMedia> lstCartMedia;
    private static Cart cartInstance;
    private final MediaDAO mediaService;

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
        mediaService = DAOFactory.getMediaDAO();
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
        for (CartMedia cartMedia : lstCartMedia) {
            int mediaId = cartMedia.getMedia().getId();
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = mediaService.getMediaById(mediaId).getQuantity();
            if (requiredQuantity > availQuantity) {
                throw new MediaNotAvailableException("Media with ID " + mediaId + " not available in required quantity");
            }
        }
    }

    public int calSubtotal() {
        int total = 0;
        for (CartMedia cm : lstCartMedia) {
            total += cm.getPrice() * cm.getQuantity();
        }
        return total;
    }
}
