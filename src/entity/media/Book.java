package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.AIMSDB;

public class Book extends Media {

    String author;
    String coverType;
    String publisher;
    Date publishDate;
    int numOfPages;
    String language;
    String bookCategory;

    public Book() throws SQLException{
        stm = AIMSDB.getConnection().createStatement();
    }

    public Book(int id, String title, String category, int price, int quantity, String type, String author,
            String coverType, String publisher, Date publishDate, int numOfPages, String language,
            String bookCategory) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }

    public Book(int id, String title, String category, int price, int value, int quantity, String type, float weight, String imageURL, String author,
                String coverType, String publisher, Date publishDate, int numOfPages, String language,
                String bookCategory, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }

    // getter and setter
    public int getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCoverType() {
        return this.coverType;
    }

    public Book setCoverType(String coverType) {
        this.coverType = coverType;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public Book setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public int getNumOfPages() {
        return this.numOfPages;
    }

    public Book setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getBookCategory() {
        return this.bookCategory;
    }

    public Book setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }
    
    public Book getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "Book " +
                "JOIN Media " +
                "ON Media.id = Book.id " +
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
            float weight = res.getFloat("weight");
            String imageUrl = res.getString("imageUrl");

            // from Book table
            String author = res.getString("author");
            String coverType = res.getString("coverType");
            String publisher = res.getString("publisher");
            Date publishDate = res.getDate("publishDate");
            int numOfPages = res.getInt("numOfPages");
            String language = res.getString("language");
            String bookCategory = res.getString("bookCategory");

            return new Book(id, title, category, price, value, quantity, type, weight, imageUrl,
                    author, coverType, publisher, publishDate, numOfPages, language, bookCategory, supportForRushDelivery);
        } else {
            throw new SQLException();
        }
    }
    

    public String toString() {
        return "{" +
            super.toString() +
            " author='" + author + "'" +
            ", coverType='" + coverType + "'" +
            ", publisher='" + publisher + "'" +
            ", publishDate='" + publishDate + "'" +
            ", numOfPages='" + numOfPages + "'" +
            ", language='" + language + "'" +
            ", bookCategory='" + bookCategory + "'" +
            "}";
    }

    public String createBookQuery(String author, String coverType, String publisher, String publishDate, int numberPages, String language, String category) throws SQLException {
        String queryValues = "(" +
                "placeForId" + ", " +
                "'" + author + "'" + ", " +
                "'" + coverType + "'" + ", " +
                "'" + publisher + "'" + ", " +
                "'" + publishDate + "'" + ", " +
                numberPages + ", " +
                "'" + language + "'" + ", " +
                "'" + category + "'" + ")";
        String sql = "INSERT INTO Book "
                + "(id, author, coverType, publisher, publishDate, numOfPages, language, bookCategory)"
                + " VALUES "
                + queryValues + ";";
        return sql;
    }

    public void deleteMediaFieldById(int id) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("DELETE FROM " + "Book" + " WHERE id = " + id + ";");
    }
}

