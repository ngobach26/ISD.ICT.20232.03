package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            String bookCategory, int rushDelivery) throws SQLException{
        super(id, title, category, price, quantity, type, rushDelivery);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }
    public Book(String author, String coverType, String publisher, Date publishDate, int numOfPages, String language, String bookCategory) throws SQLException {
        super();
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }

    public Book(int id, String title, String category, int price, int value, int quantity, String type, String imageURL, String author,
                String coverType, String publisher, Date publishDate, int numOfPages, String language,
                String bookCategory, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, type, imageURL, supportForRushDelivery);
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

    public List getAllMedia() {
        return null;
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
}

