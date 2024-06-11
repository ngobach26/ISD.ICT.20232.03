package entity.media;

import java.sql.SQLException;
import java.util.Date;


public class LPRecord extends Media {
    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;
    public LPRecord() throws SQLException {
    }
    public LPRecord(int id, String title, String category, int price, int quantity, String type, String artist, String recordLabel, String musicType, Date releasedDate) throws SQLException {
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public LPRecord(int id, String title, String category, int price, int value, int quantity, float weight, String type, String imageURL, int supportForRushDelivery, String artist, String recordLabel, String musicType, Date releasedDate) throws SQLException {
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
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

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }
}
