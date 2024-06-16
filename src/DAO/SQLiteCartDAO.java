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
import services.DAOService.MediaService;

public class SQLiteCartDAO implements CartDAO {
    private static final Logger LOGGER = Logger.getLogger(SQLiteCartDAO.class.getName());
    private final Connection connection;
    private static final String INSERT_CART = "INSERT INTO CART (cartID, userID) VALUES (?, ?)";
    private static final String INSERT_CART_MEDIA = "INSERT INTO CART_MEDIA (cartID, mediaID, number_of_products) VALUES (?, ?, ?)";
    private static final String GET_CART_ID = "SELECT cartID FROM CART WHERE userID = ?";
    private static final String GET_CART_COUNT = "SELECT COUNT(*) AS cartCount FROM CART";
    private static final String GET_CART_MEDIA_ITEMS = "SELECT mediaID, number_of_products FROM CART_MEDIA WHERE cartID = ?";

    public SQLiteCartDAO() throws SQLException {
        this.connection = AIMSDB.getConnection();
    }

    public void saveCart(Cart cart) throws SQLException {
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
            throw e;
        } finally {
            if (cartStmt != null) {
                cartStmt.close();
            }
            if (cartMediaStmt != null) {
                cartMediaStmt.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }


    public int getCartID(int userId) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(GET_CART_ID);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cartID");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return -1; // No cart found
    }

    private int getCartCount() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(GET_CART_COUNT);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cartCount");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return 0; // Default to 0 if no carts found
    }

    public List<CartMedia> getCartMediaItems(int cartId) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CartMedia> cartMediaList = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(GET_CART_MEDIA_ITEMS);
            stmt.setInt(1, cartId);
            rs = stmt.executeQuery();

            MediaService mediaService = MediaService.getInstance();

            while (rs.next()) {
                int mediaID = rs.getInt("mediaID");
                int numberOfProducts = rs.getInt("number_of_products");
                Media media = mediaService.getMediaById(mediaID);  // Assume this method exists in MediaService
                CartMedia cartMedia = new CartMedia(cartId, media, numberOfProducts, media.getPrice());
                cartMediaList.add(cartMedia);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return cartMediaList;
    }

}
