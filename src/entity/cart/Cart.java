package entity.cart;

import DAO.CartDAO;
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
    private static Cart cartInstance;
    private final List<CartMedia> lstCartMedia;
    private final MediaDAO mediaDAO;

    public static Cart getCart(int userId) {
        if (cartInstance == null || cartInstance.getUserId() != userId) {
            cartInstance = new Cart(userId);
        }
        return cartInstance;
    }

    public Cart(int userId){
        this.userId = userId;
        this.lstCartMedia = new ArrayList<>();
        this.mediaDAO = DAOFactory.getMediaDAO();

        // Retrieve the cart ID from the database
        CartDAO cartDAO = DAOFactory.getCartDAO();
        this.cartId = cartDAO.getCartID(userId);

        // Retrieve the cart media items from the database and populate lstCartMedia
        this.lstCartMedia.addAll(cartDAO.getCartMediaItems(this.cartId));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Cart() {
        lstCartMedia = new ArrayList<>();
        mediaDAO = DAOFactory.getMediaDAO();
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

    public int calSubtotal() {
        int total = 0;
        for (CartMedia cm : lstCartMedia) {
            total += cm.getPrice() * cm.getQuantity();
        }
        return total;
    }

    public void checkAvailabilityOfProduct() throws SQLException, MediaNotAvailableException {
        for (CartMedia cartMedia : lstCartMedia) {
            int mediaId = cartMedia.getMedia().getId();
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = mediaDAO.getMediaById(mediaId).getQuantity();
            if (requiredQuantity > availQuantity) {
                throw new MediaNotAvailableException("Media with ID " + mediaId + " not available in required quantity");
            }
        }
    }
}
