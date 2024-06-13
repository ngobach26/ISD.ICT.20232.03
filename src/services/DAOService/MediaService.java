package services.DAOService;
import DAO.MediaDAO;
import common.exception.MediaNotAvailableException;
import entity.cart.CartMedia;
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

    public void createMedia(Media media) throws SQLException {
        mediaDAO.createMedia(media);
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

}
