package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import db.AIMSDB;


public class LPRecord extends Media {
    String artist;
    String recordLabel;
    String genre;
    Date releasedDate;
    public LPRecord() throws SQLException {
    }
    public LPRecord(int id, String title, String category, int price, int quantity, String type, String artist, String recordLabel, String genre, Date releasedDate) throws SQLException {
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.genre = genre;
        this.releasedDate = releasedDate;
    }

    public LPRecord(int id, String title, String category, int price, int value, int quantity, float weight, String type, String imageURL, int supportForRushDelivery, String artist, String recordLabel, String genre, Date releasedDate) throws SQLException {
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.genre = genre;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "LPRecord " +
                "JOIN Media " +
                "ON Media.id = LPRecord.id " +
                "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            // from media table
            String title = res.getString("title");
            String type = res.getString("type");
            int price = res.getInt("price");
            int value = res.getInt("value");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            float weight = res.getFloat("weight");
            String imageUrl = res.getString("imageUrl");

            // from LPRecorf table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String genre = res.getString("genre");
            Date releasedDate = res.getDate("releasedDate");

            return new CD(id, title, category, price, value, quantity, type, weight, imageUrl,
                    artist, recordLabel, genre, releasedDate, supportForRushDelivery);

        } else {
            throw new SQLException();
        }
    }

    
    public String toString() {
        return "{" +
            super.toString() +
            " artist='" + artist + "'" +
            ", recordLabel='" + recordLabel + "'" +
            ", genreType='" + genre + "'" +
            ", releasedDate='" + releasedDate + "'" +
            "}";
    }

    public String createLPRecordQuery(String artist, String recordLabel, String genre, String releasedDate) throws SQLException {
        StringBuilder queryValues = new StringBuilder();
        queryValues.append("(")
                .append("placeForId").append(", ")
                .append("'").append(artist).append("'").append(", ")
                .append("'").append(recordLabel).append("'").append(", ")
                .append("'").append(genre).append("'").append(", ")
                .append("'").append(releasedDate).append("'").append(")");
        String sql = "INSERT INTO LPRecord "
                + "(id, artist, recordLabel, genre, releasedDate)"
                + " VALUES "
                + queryValues.toString() + ";";
        return sql;
    }

    @Override
    public void deleteMediaFieldById(int id) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("DELETE FROM " + "LPRecord" + " WHERE id = " + id + ";");
    }
}