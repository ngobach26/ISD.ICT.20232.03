package entity.media;

import java.sql.SQLException;
import java.util.Date;

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
    
    public String createCDQuery(String artist, String recordLabel, String musicType, String releaseDate) throws SQLException {
        StringBuilder queryValues = new StringBuilder();
        queryValues.append("(")
                .append("placeForId").append(", ")
                .append("'").append(artist).append("'").append(", ")
                .append("'").append(recordLabel).append("'").append(", ")
                .append("'").append(musicType).append("'").append(", ")
                .append("'").append(releaseDate).append("'").append(")");
        String sql = "INSERT INTO CD "
                + "(id, artist, recordLabel, musicType, releasedDate)"
                + " VALUES "
                + queryValues.toString() + ";";
        return sql;
    }
}
