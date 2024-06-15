package DAO;

import java.sql.SQLException;

public interface BookDAO {
    public void deleteMediaFieldById(int id) throws SQLException;
    public String createBookQuery(String author, String coverType, String publisher, String publishDate, int numberPages, String language, String category) throws SQLException;
}
