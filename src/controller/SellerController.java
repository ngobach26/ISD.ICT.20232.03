package controller;

import java.sql.SQLException;
import java.util.List;

import DAO.IMediaDAO;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import services.DAOFactory;

public class SellerController extends BaseController {

    private final IMediaDAO mediaDAO;

    public SellerController() {
        this.mediaDAO = DAOFactory.getMediaDAO();
    }

    public List<Media> getAllMedia() throws SQLException{
        return new Media().getAllMedia();
    }

    public void deleteMediaById(int id) throws SQLException {
        mediaDAO.deleteMediaById(id);
    }
    public CD getCDById(int id) throws SQLException{
        return mediaDAO.getCDById(id);
    }

    public Book getBookById(int id) throws SQLException{
        return mediaDAO.getBookById(id);
    }

    public int createMedia(Media media) throws SQLException {
        return mediaDAO.createMedia(media);
    }

    public void createBook(Book book) throws SQLException{
        mediaDAO.createBook(book);
    }

    public void createCD(CD cd) throws SQLException{
        mediaDAO.createCD(cd);
    }

    public void createDVD(DVD dvd) throws SQLException{
        mediaDAO.createDVD(dvd);
    }

    public void updateBook(Book book) throws SQLException {
        // Assuming you have a method in mediaDAO to update a book
        mediaDAO.updateBook(book);
    }

    public void updateCD(CD cd) throws SQLException {
        // Assuming you have a method in mediaDAO to update a CD
        mediaDAO.updateCD(cd);
    }

    public void updateDVD(DVD dvd) throws SQLException {
        // Assuming you have a method in mediaDAO to update a DVD
        mediaDAO.updateDVD(dvd);
    }

    public void updateMedia(Media media) throws SQLException {
        // Assuming you have a method in mediaDAO to update a DVD
        mediaDAO.updateMedia(media);
    }

    public DVD getDVDById(int id) throws SQLException{
        return mediaDAO.getDVDById(id);
    }
    public void deleteDVDById(int id) throws SQLException{
        mediaDAO.deleteDVDById(id);
    }
    public void deleteCDById(int id) throws SQLException{
        mediaDAO.deleteCDById(id);
    }
    public void deleteBookById(int id) throws SQLException{
        mediaDAO.deleteBookById(id);
    }






}
