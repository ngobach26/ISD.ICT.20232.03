package DAO;

import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

public interface MediaDAO {
    Media getMediaById(int id) throws SQLException;
    List<Media> searchMedia(String title) throws SQLException;
    List<Media> getAllMedia() throws SQLException;
    int createMedia(Media media) throws SQLException;
    void updateMedia(Media media) throws SQLException;
    void updateMediaFieldById(int id, String field, Object value) throws SQLException;
    void deleteMediaById(int id) throws SQLException;
    CD getCDById(int id) throws SQLException;
    Book getBookById(int id) throws SQLException;
    void createBook(Book book) throws SQLException;
    void createCD(CD cd) throws SQLException;
    void createDVD(DVD DVD) throws SQLException;

    void updateBook(Book book) throws SQLException;
    void updateCD(CD cd) throws SQLException;
    void updateDVD(DVD DVD) throws SQLException;
    public DVD getDVDById(int id) throws SQLException;

}

