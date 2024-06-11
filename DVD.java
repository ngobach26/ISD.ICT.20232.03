package entity.media;

import java.sql.SQLException;
import java.util.Date;

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
            String director, int runtime, String studio, String language, String subtitles, Date releasedDate, String filmType) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }

    public DVD(int id, String title, String category, int price, int value, int quantity, String type, float weight, String imageURL, String discType,
               String director, int runtime, String studio, String language, String subtitles, Date releasedDate, String filmType, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
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
}
