package DAO;

import db.AIMSDB;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
        // Create and add a fixed Media object
        Media fixedMedia = new Media()
                .setId(999)  // Example fixed ID
                .setTitle("Fixed Media Title")  // Example fixed title
                .setQuantity(100)  // Example fixed quantity
                .setCategory("Fixed Category")  // Example fixed category
                .setMediaURL("http://example.com/fixed-media.jpg")  // Example fixed URL
                .setPrice(100)  // Example fixed price
                .setType("Fixed Type");  // Example fixed type
                // .setWeight(0.5f);  // Example fixed weight
        list.add(fixedMedia);

        return list;
    }

    @Override
    public int createMedia(Media media) throws SQLException {
        String sql = "INSERT INTO Media (title, category, price, quantity, type, imageUrl, value, support_for_rush_delivery) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, media.getTitle());
        statement.setString(2, media.getCategory());
        statement.setInt(3, media.getPrice());
        statement.setInt(4, media.getQuantity());
        statement.setString(5, media.getType());
        statement.setString(6, media.getImageURL());
        statement.setInt(7, media.getValue());
        statement.setInt(8, media.getSupportForRushDelivery());
        statement.executeUpdate();
        ResultSet res = statement.getGeneratedKeys();
        return res.getInt(1);
    }

    public void updateMedia(Media media) throws SQLException {
        String sql = "UPDATE Media SET title=?, category=?, price=?, quantity=?, type=?, imageUrl=?, value=?, support_for_rush_delivery=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, media.getTitle());
        statement.setString(2, media.getCategory());
        statement.setInt(3, media.getPrice());
        statement.setInt(4, media.getQuantity());
        statement.setString(5, media.getType());
        statement.setString(6, media.getImageURL());
        statement.setInt(7, media.getValue());
        statement.setInt(8, media.getSupportForRushDelivery());
        statement.setInt(9, media.getId());
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

    public Book getBookById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "Books " +
                "JOIN Media " +
                "ON Media.id = Books.id " +
                "WHERE Media.id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            // from Media table
            String title = res.getString("title");
            String type = res.getString("type");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            String imageUrl = res.getString("imageUrl");

            // from Book table
            String author = res.getString("author");
            String coverType = res.getString("coverType");
            String publisher = res.getString("publisher");
            Date publishDate = res.getDate("publishDate");
            int numOfPages = res.getInt("numOfPages");
            String language = res.getString("language");
            String bookCategory = res.getString("bookCategory");
            int supportForRushDelivery = res.getInt("support_for_rush_delivery"); // assuming this is an integer

            return new Book(id, title, category, price, value, quantity, type, imageUrl, author, coverType, publisher, publishDate, numOfPages, language, bookCategory, supportForRushDelivery);
        } else {
            throw new SQLException("Book not found with id: " + id);
        }
    }

    @Override
    public void createBook(Book book) throws SQLException {
        // SQL query to insert a new book into the Books table
        String sql = "INSERT INTO Books (id, author, coverType, publisher, publishDate, numOfPages, language, bookCategory) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        // Prepare the statement with the connection
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Set the parameters for the statement
        statement.setInt(1, book.getId());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getCoverType());
        statement.setString(4, book.getPublisher());
        statement.setDate(5, new java.sql.Date(book.getPublishDate().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setInt(6, book.getNumOfPages());
        statement.setString(7, book.getLanguage());
        statement.setString(8, book.getBookCategory());
        statement.executeUpdate();
    }


    @Override
    public void createCD(CD cd) throws SQLException {
        // SQL query to insert a new CD into the CD table
        String sql = "INSERT INTO CD (id, artist, recordLabel, musicType, releasedDate) VALUES (?, ?, ?, ?, ?);";

        // Prepare the statement with the connection
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Set the parameters for the statement
        statement.setInt(1, cd.getId());
        statement.setString(2, cd.getArtist());
        statement.setString(3, cd.getRecordLabel());
        statement.setString(4, cd.getMusicType());
        statement.setDate(5, new java.sql.Date(cd.getReleasedDate().getTime())); // Convert java.util.Date to java.sql.Date

        // Execute the SQL statement
        statement.executeUpdate();
    }

    @Override
    public void createDVD(DVD dvd) throws SQLException {
        // SQL query to insert a new DVD into the DVD table
        String sql = "INSERT INTO DVD (id, discType, director, runtime, studio, language, subtitle, releasedDate, filmType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        // Prepare the statement with the connection
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Set the parameters for the statement
        statement.setInt(1, dvd.getId());
        statement.setString(2, dvd.getDiscType());
        statement.setString(3, dvd.getDirector());
        statement.setInt(4, dvd.getRuntime());
        statement.setString(5, dvd.getStudio());
        statement.setString(6, dvd.getLanguage());
        statement.setString(7, dvd.getSubtitles());
        statement.setDate(8, new java.sql.Date(dvd.getReleasedDate().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setString(9, dvd.getFilmType());

        // Execute the SQL statement
        statement.executeUpdate();
    }

    public CD getCDById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "CD " +
                "JOIN Media " +
                "ON Media.id = CD.id " +
                "WHERE Media.id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            // from Media table
            String title = res.getString("title");
            String type = res.getString("type");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            String imageUrl = res.getString("imageUrl");

            // from CD table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");
            int supportForRushDelivery = res.getInt("support_for_rush_delivery"); // assuming this is an integer

            return new CD(id, title, category, price, value, quantity, type, imageUrl, artist, recordLabel, musicType, releasedDate, supportForRushDelivery);
        } else {
            throw new SQLException("CD not found with id: " + id);
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE Books SET author = ?, coverType = ?, publisher = ?, publishDate = ?, numOfPages = ?, language = ?, bookCategory = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getAuthor());
        statement.setString(2, book.getCoverType());
        statement.setString(3, book.getPublisher());
        statement.setDate(4, new java.sql.Date(book.getPublishDate().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setInt(5, book.getNumOfPages());
        statement.setString(6, book.getLanguage());
        statement.setString(7, book.getBookCategory());
        statement.setInt(8, book.getId());
        statement.executeUpdate();
    }

    public void updateCD(CD cd) throws SQLException {
        String sql = "UPDATE CD SET artist = ?, recordLabel = ?, musicType = ?, releasedDate = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cd.getArtist());
        statement.setString(2, cd.getRecordLabel());
        statement.setString(3, cd.getMusicType());
        statement.setDate(4, new java.sql.Date(cd.getReleasedDate().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setInt(5, cd.getId());
        statement.executeUpdate();
    }

    public void updateDVD(DVD dvd) throws SQLException {
        String sql = "UPDATE DVD SET discType = ?, director = ?, runtime = ?, studio = ?, language = ?, subtitle = ?, releasedDate = ?, filmType = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dvd.getDiscType());
        statement.setString(2, dvd.getDirector());
        statement.setInt(3, dvd.getRuntime());
        statement.setString(4, dvd.getStudio());
        statement.setString(5, dvd.getLanguage());
        statement.setString(6, dvd.getSubtitles());
        statement.setDate(7, new java.sql.Date(dvd.getReleasedDate().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setString(8, dvd.getFilmType());
        statement.setInt(9, dvd.getId());
        statement.executeUpdate();
    }
    public DVD getDVDById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "DVD " +
                "JOIN Media " +
                "ON Media.id = DVD.id " +
                "WHERE Media.id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            // from Media table
            String title = res.getString("title");
            String type = res.getString("type");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            String imageUrl = res.getString("imageUrl");

            // from DVD table
            String director = res.getString("director");
            int runtime = res.getInt("runtime");
            String studio = res.getString("studio");
            String language = res.getString("language");
            String subtitles = res.getString("subtitle");
            String discType = res.getString("discType");
            String filmType = res.getString("filmType");
            Date releasedDate = res.getDate("releasedDate");
            int support_for_rush_delivery = res.getInt("support_for_rush_delivery");

            return new DVD(id, title, category, price, value, quantity, type, imageUrl, director, runtime, studio, language, subtitles, discType, filmType, releasedDate,support_for_rush_delivery);
        } else {
            throw new SQLException("DVD not found with id: " + id);
        }
    }
}

