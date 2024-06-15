package DAO;

import db.AIMSDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import entity.cart.Cart;
import entity.cart.CartMedia;

public class SQLiteCartDAO implements CartDAO{
    private static final Logger LOGGER = Logger.getLogger(SQLiteCartDAO.class.getName());
    private final Connection connection;
    private static final String INSERT_CART = "INSERT INTO CART (cartID, userID) VALUES (?, ?)";
    private static final String INSERT_CART_MEDIA = "INSERT INTO CART_MEDIA (cartID, mediaID, number_of_products) VALUES (?, ?, ?)";
    private static final String GET_CART_ID = "SELECT cartID FROM CART WHERE userID = ?";
    private static final String GET_CART_COUNT = "SELECT COUNT(*) AS cartCount FROM CART";

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
                connection.rollback();
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
            if (connection != null) {
                connection.close();
            }
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
}
