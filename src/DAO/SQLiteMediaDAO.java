package DAO;

import db.AIMSDB;
import entity.media.Media;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SQLiteMediaDAO implements MediaDAO {
    private static final Logger LOGGER = Logger.getLogger(SQLiteMediaDAO.class.getName());
    private final Connection connection;

    public SQLiteMediaDAO() throws SQLException {
        this.connection = AIMSDB.getConnection();
    }

    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
                    // .setWeight(res.getFloat("weight"));
        }
        return null;
    }

    @Override
    public List<Media> searchMedia(String title) throws SQLException {
        String sql = "SELECT * FROM Media WHERE title LIKE ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + title + "%");
        ResultSet res = statement.executeQuery();
        List<Media> list = new ArrayList<>();
        while (res.next()) {
            Media m = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
                    // .setWeight(res.getFloat("weight"));
            list.add(m);
        }
        return list;
    }

    @Override
    public List<Media> getAllMedia() throws SQLException {
        String sql = "SELECT * FROM Media;";
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(sql);
        List<Media> list = new ArrayList<>();
        while (res.next()) {
            Media m = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
            list.add(m);
        }
        return list;
    }

    @Override
    public void createMedia(Media media) throws SQLException {
        String sql = "INSERT INTO Media (title, category, price, quantity, type, imageUrl, value, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, media.getTitle());
        statement.setString(2, media.getCategory());
        statement.setInt(3, media.getPrice());
        statement.setInt(4, media.getQuantity());
        statement.setString(5, media.getType());
        statement.setString(6, media.getImageURL());
        statement.setInt(7, media.getValue());
        statement.setFloat(8, media.getWeight());
        statement.executeUpdate();
    }

    @Override
    public void updateMediaFieldById(int id, String field, Object value) throws SQLException {
        String sql = "UPDATE Media SET " + field + " = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        if (value instanceof String) {
            statement.setString(1, (String) value);
        } else if (value instanceof Integer) {
            statement.setInt(1, (Integer) value);
        } else if (value instanceof Float) {
            statement.setFloat(1, (Float) value);
        }
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    @Override
    public void deleteMediaById(int id) throws SQLException {
        String sql = "DELETE FROM Media WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}

