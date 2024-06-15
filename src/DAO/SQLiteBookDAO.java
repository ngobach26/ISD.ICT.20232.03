package DAO;

import java.sql.SQLException;

public class SQLiteBookDAO implements BookDAO{
    @Override
    public void deleteMediaFieldById(int id) throws SQLException {

    }

    @Override
    public String createBookQuery(String author, String coverType, String publisher, String publishDate, int numberPages, String language, String category) throws SQLException {
        return null;
    }
}
