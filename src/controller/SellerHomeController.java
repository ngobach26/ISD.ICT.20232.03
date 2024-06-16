package controller;

import java.sql.SQLException;
import java.util.List;

import DAO.MediaDAO;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import services.DAOService.DAOFactory;
import services.DAOService.MediaService;

public class SellerHomeController extends BaseController {

    private final MediaDAO mediaService ;

    public SellerHomeController() {
        this.mediaService = DAOFactory.getMediaDAO();
    }

    public List<Media> getAllMedia() throws SQLException{
        return new Media().getAllMedia();
    }

    public void deleteMediaById(int id) throws SQLException {
        mediaService.deleteMediaById(id);
    }
    public CD getCDById(int id) throws SQLException{
        return mediaService.getCDById(id);
    }

    public Book getBookById(int id) throws SQLException{
        return mediaService.getBookById(id);
    }

    public int createMedia(Media media) throws SQLException {
        return mediaService.createMedia(media);
    }

    public void createBook(Book book) throws SQLException{
        mediaService.createBook(book);
    }

    public void createCD(CD cd) throws SQLException{
        mediaService.createCD(cd);
    }

    public void createDVD(DVD dvd) throws SQLException{
        mediaService.createDVD(dvd);
    }

    public void updateBook(Book book) throws SQLException {
        // Assuming you have a method in mediaDAO to update a book
        mediaService.updateBook(book);
    }

    public void updateCD(CD cd) throws SQLException {
        // Assuming you have a method in mediaDAO to update a CD
        mediaService.updateCD(cd);
    }

    public void updateDVD(DVD dvd) throws SQLException {
        // Assuming you have a method in mediaDAO to update a DVD
        mediaService.updateDVD(dvd);
    }

    public void updateMedia(Media media) throws SQLException {
        // Assuming you have a method in mediaDAO to update a DVD
        mediaService.updateMedia(media);
    }

    public DVD getDVDById(int id) throws SQLException{
        return mediaService.getDVDById(id);
    }





}
