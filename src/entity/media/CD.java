package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import db.AIMSDB;

public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException{

    }

    public CD(int id, String title, String category, int price, int quantity, String type, String artist,
            String recordLabel, String musicType, Date releasedDate) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public CD(int id, String title, String category, int price, int value, int quantity, String type, float weight, String imageURL, String artist,
              String recordLabel, String musicType, Date releasedDate, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }
    
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "CD " +
                "JOIN Media " +
                "ON Media.id = CD.id " +
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

            // from CD table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");

            return new CD(id, title, category, price, value, quantity, type, weight, imageUrl,
                    artist, recordLabel, musicType, releasedDate, supportForRushDelivery);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public List getAllMedia() {
        return null;
    }

    public String createCDQuery(String artist, String recordLabel, String musicType, String releaseDate) throws SQLException {
        String queryValues = "(" +
                "placeForId" + ", " +
                "'" + artist + "'" + ", " +
                "'" + recordLabel + "'" + ", " +
                "'" + musicType + "'" + ", " +
                "'" + releaseDate + "'" + ")";
        String sql = "INSERT INTO CD "
                + "(id, artist, recordLabel, musicType, releasedDate)"
                + " VALUES "
                + queryValues + ";";
        return sql;
    }

    @Override
    public void deleteMediaFieldById(int id) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("DELETE FROM " + "CD" + " WHERE id = " + id + ";");
    }
}
