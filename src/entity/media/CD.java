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
            String recordLabel, String musicType, Date releasedDate, int rushDelivery) throws SQLException{
        super(id, title, category, price, quantity, type, rushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public CD(int id, String title, String category, int price, int value, int quantity, String type, String imageURL, String artist,
              String recordLabel, String musicType, Date releasedDate, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, type, imageURL, supportForRushDelivery);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public CD(String artist, String recordLabel, String musicType, Date releaseDate) throws SQLException {
        super();
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releaseDate;
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

    @Override
    public List getAllMedia() {
        return null;
    }

}
