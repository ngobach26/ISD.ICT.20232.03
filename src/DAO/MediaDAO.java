package DAO;

import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

public interface MediaDAO {
    Media getMediaById(int id) throws SQLException;
    List<Media> searchMedia(String title) throws SQLException;
    List<Media> getAllMedia() throws SQLException;
    void createMedia(Media media) throws SQLException;
    void updateMediaFieldById(int id, String field, Object value) throws SQLException;
    void deleteMediaById(int id) throws SQLException;
}

