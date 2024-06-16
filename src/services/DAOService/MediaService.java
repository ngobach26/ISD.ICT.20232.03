package services.DAOService;
import DAO.MediaDAO;
import common.exception.MediaNotAvailableException;
import entity.cart.CartMedia;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

public class MediaService {
    private static MediaService instance;
    private final MediaDAO mediaDAO;

    public MediaService() throws SQLException {
        this.mediaDAO = DAOFactory.getMediaDAO();
    }
    public static MediaService getInstance() {
        try {
            // Check if an instance already exists, if not, create a new one
            if (instance == null) {
                instance = new MediaService();
            }
        } catch (SQLException e) {
            // Handle the exception, log or throw a custom exception if needed
            // For example:
            e.printStackTrace();
        }
        return instance;
    }

    public Media getMediaById(int id) throws SQLException {
        return mediaDAO.getMediaById(id);
    }

    public List<Media> searchMedia(String title) throws SQLException {
        return mediaDAO.searchMedia(title);
    }

    public List<Media> getAllMedia() throws SQLException {
        return mediaDAO.getAllMedia();
    }

    public int  createMedia(Media media) throws SQLException {
        return mediaDAO.createMedia(media);
    }
    public void updateMedia(Media media) throws SQLException {
        mediaDAO.updateMedia(media);
    }

    public void updateMediaFieldById(int id, String field, Object value) throws SQLException {
        mediaDAO.updateMediaFieldById(id, field, value);
    }

    public void deleteMediaById(int id) throws SQLException {
        mediaDAO.deleteMediaById(id);
    }

    public void checkAvailabilityOfProduct(List<CartMedia> cartMediaList) throws SQLException, MediaNotAvailableException {
        for (CartMedia cartMedia : cartMediaList) {
            int mediaId = cartMedia.getMedia().getId();
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = mediaDAO.getMediaById(mediaId).getQuantity();
            if (requiredQuantity > availQuantity) {
                throw new MediaNotAvailableException("Media with ID " + mediaId + " not available in required quantity");
            }
        }
    }

    public CD getCDById(int id) throws SQLException{
        return mediaDAO.getCDById(id);
    }

    public Book getBookById(int id) throws SQLException{
        return mediaDAO.getBookById(id);
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
        mediaDAO.updateBook(book);
    }

    public void updateCD(CD cd) throws SQLException {
        mediaDAO.updateCD(cd);
    }

    public void updateDVD(DVD dvd) throws SQLException {
        mediaDAO.updateDVD(dvd);
    }
    public DVD getDVDById(int id) throws SQLException{
        return mediaDAO.getDVDById(id);
    }






}
