package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import db.AIMSDB;

public class DVD extends Media {

    String discType;
    String director;
    int runtime;
    String studio;
    String language;
    String subtitles;
    Date releasedDate;
    String filmType;

    public DVD() throws SQLException{

    }

    public DVD(int id, String title, String category, int price, int quantity, String type, String discType,
            String director, int runtime, String studio, String language, String subtitles, Date releasedDate, String filmType, int rushDelivery) throws SQLException{
        super(id, title, category, price, quantity, type, rushDelivery);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }

    public DVD(int id, String title, String category, int price, int value, int quantity, String type, String imageURL,
               String director, int runtime, String studio, String language, String subtitles,String discType,String filmType, Date releasedDate,int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, type, imageURL, supportForRushDelivery);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }


    public DVD(String discType, String director, int runtime, String studio, String subtitles, Date releaseDate, String filmType) throws SQLException {
        super();
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitles = subtitles;
        this.releasedDate = releaseDate;
        this.filmType = filmType;
    }
    public String getDiscType() {
        return this.discType;
    }

    public DVD setDiscType(String discType) {
        this.discType = discType;
        return this;
    }

    public String getDirector() {
        return this.director;
    }

    public DVD setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public DVD setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getStudio() {
        return this.studio;
    }

    public DVD setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getSubtitles() {
        return this.subtitles;
    }

    public DVD setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public DVD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }


    public String getFilmType() {
        return this.filmType;
    }

    public DVD setFilmType(String filmType) {
        this.filmType = filmType;
        return this;
    }

    public List getAllMedia() {
        return null;
    }

}
