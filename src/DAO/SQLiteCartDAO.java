package DAO;

import db.AIMSDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import services.DAOFactory;

public class SQLiteCartDAO implements CartDAO {
    private static final Logger LOGGER = Logger.getLogger(SQLiteCartDAO.class.getName());
    private final Connection connection;
    private static final String INSERT_CART = "INSERT INTO CART (cartID, userID) VALUES (?, ?)";
    private static final String INSERT_CART_MEDIA = "INSERT INTO CART_MEDIA (cartID, mediaID, number_of_products) VALUES (?, ?, ?)";
    private static final String GET_CART_ID = "SELECT cartID FROM CART WHERE userID = ?";
    private static final String GET_CART_COUNT = "SELECT COUNT(*) AS cartCount FROM CART";
    private static final String GET_CART_MEDIA_ITEMS = "SELECT mediaID, number_of_products FROM CART_MEDIA WHERE cartID = ?";
    private static final String UPDATE_CART_MEDIA = "UPDATE CART_MEDIA SET number_of_products = ? WHERE cartID = ? AND mediaID = ?";
    private static final String DELETE_CART_MEDIA = "DELETE FROM CART_MEDIA WHERE cartID = ? AND mediaID = ?";

    public SQLiteCartDAO() {
        this.connection = AIMSDB.getConnection();
    }

    public void saveCart(Cart cart) {
        PreparedStatement cartStmt = null;
        PreparedStatement cartMediaStmt = null;

        try {
            connection.setAutoCommit(false);

            // Get the cartID if it exists, otherwise calculate a new cartID
            int cartID = getCartID(cart.getUserId());
            if (cartID == -1) {
                cartID = getCartCount() + 1;  // Calculate new cartID

                cartStmt = connection.prepareStatement(INSERT_CART);
                cartStmt.setInt(1, cartID);
                cartStmt.setInt(2, cart.getUserId());
                cartStmt.executeUpdate();
            }

            // Insert into CART_MEDIA
            cartMediaStmt = connection.prepareStatement(INSERT_CART_MEDIA);
            for (CartMedia cartMedia : cart.getListMedia()) {
                cartMediaStmt.setInt(1, cartID);
                cartMediaStmt.setInt(2, cartMedia.getMedia().getId());
                cartMediaStmt.setInt(3, cartMedia.getQuantity());
                cartMediaStmt.addBatch();
            }
            cartMediaStmt.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    LOGGER.severe("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            LOGGER.severe("Failed to save cart: " + e.getMessage());
        }
    }

    public int getCartID(int userId) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(GET_CART_ID);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cartID");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching cart ID: " + e.getMessage());
        }
        return -1; // No cart found
    }

    private int getCartCount() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = connection.prepareStatement(GET_CART_COUNT);
        rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("cartCount");
        }
        return 0; // Default to 0 if no carts found
    }

    public List<CartMedia> getCartMediaItems(int cartId) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CartMedia> cartMediaList = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(GET_CART_MEDIA_ITEMS);
            stmt.setInt(1, cartId);
            rs = stmt.executeQuery();

            MediaDAO mediaDAO = DAOFactory.getMediaDAO();

            while (rs.next()) {
                int mediaID = rs.getInt("mediaID");
                int numberOfProducts = rs.getInt("number_of_products");
                Media media = mediaDAO.getMediaById(mediaID);  // Assume this method exists in MediaDAO
                CartMedia cartMedia = new CartMedia(cartId, media, numberOfProducts, media.getPrice());
                cartMediaList.add(cartMedia);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching cart media items: " + e.getMessage());
        }
        return cartMediaList;
    }

    public void addMediaToCart(int userId, Media media, int quantity) {
        PreparedStatement stmt = null;
        try {
            int cartID = getCartID(userId);
            if (cartID == -1) {
                cartID = getCartCount() + 1;
                saveCart(new Cart(userId));  // Create a new cart if it doesn't exist
            }

            CartMedia cartMedia = getCartMedia(cartID, media.getId());
            if (cartMedia != null) {
                // Update existing cart media
                stmt = connection.prepareStatement(UPDATE_CART_MEDIA);
                stmt.setInt(1, cartMedia.getQuantity() + quantity);
                stmt.setInt(2, cartID);
                stmt.setInt(3, media.getId());
                stmt.executeUpdate();
            } else {
                // Insert new cart media
                stmt = connection.prepareStatement(INSERT_CART_MEDIA);
                stmt.setInt(1, cartID);
                stmt.setInt(2, media.getId());
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.severe("Error adding media to cart: " + e.getMessage());
        }
    }

    public void removeMediaFromCart(int userId, int mediaId) {
        PreparedStatement stmt = null;
        try {
            int cartID = getCartID(userId);
            if (cartID != -1) {
                stmt = connection.prepareStatement(DELETE_CART_MEDIA);
                stmt.setInt(1, cartID);
                stmt.setInt(2, mediaId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.severe("Error removing media from cart: " + e.getMessage());
        }
    }

    private CartMedia getCartMedia(int cartID, int mediaID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(GET_CART_MEDIA_ITEMS);
            stmt.setInt(1, cartID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("mediaID") == mediaID) {
                    int numberOfProducts = rs.getInt("number_of_products");
                    Media media = DAOFactory.getMediaDAO().getMediaById(mediaID);
                    return new CartMedia(cartID, media, numberOfProducts, media.getPrice());
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching cart media: " + e.getMessage());
        }
        return null;
    }

    public void deleteAllCartMediaForUser(int userId) {
        PreparedStatement stmt = null;

        try {
            // Get the cartID associated with the user
            int cartID = getCartID(userId);

            if (cartID != -1) { // If cartID exists
                // Delete all CART_MEDIA items with the cartID
                stmt = connection.prepareStatement(DELETE_CART_MEDIA);
                stmt.setInt(1, cartID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.severe("Error deleting CART_MEDIA for user: " + e.getMessage());
        }
    }
}
