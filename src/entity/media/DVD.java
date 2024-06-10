package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class DVD extends Media {

    String discType;
    String director;
    int runtime;
    String studio;
    String subtitles;
    Date releasedDate;
    String filmType;

    public DVD() throws SQLException{

    }

    public DVD(int id, String title, String category, int price, int quantity, String type, String discType,
            String director, int runtime, String studio, String subtitles, Date releasedDate, String filmType) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }

    public DVD(int id, String title, String category, int price, int value, int quantity, String type, float weight, String imageURL, String discType,
               String director, int runtime, String studio, String subtitles, Date releasedDate, String filmType) throws SQLException{
        super(id, title, category, price, value, quantity, weight, type, imageURL);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
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
    
    
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "DVD " +
                "JOIN Media " +
                "ON Media.id = DVD.id " +
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

            // from DVD table
            String discType = res.getString("discType");
            String director = res.getString("director");
            int runtime = res.getInt("runtime");
            String studio = res.getString("studio");
            String subtitles = res.getString("subtitle");
            Date releasedDate = res.getDate("releasedDate");
            String filmType = res.getString("filmType");

            return new DVD(id, title, category, price, value, quantity, type, weight, imageUrl, discType, director, runtime, studio, subtitles, releasedDate, filmType);

        } else {
            throw new SQLException();
        }
    }
    
    public String createDVDQuery(String discType, String director, int runtime, String studio, String subtitles, String releasedDate, String filmType) throws SQLException {
        StringBuilder queryValues = new StringBuilder();
        queryValues.append("(")
                .append("placeForId").append(", ")
                .append("'").append(discType).append("'").append(", ")
                .append("'").append(director).append("'").append(", ")
                .append(runtime).append(", ")
                .append("'").append(studio).append("'").append(", ")
                .append("'").append(subtitles).append("'").append(", ")
                .append("'").append(releasedDate).append("'").append(", ")
                .append("'").append(filmType).append("'").append(")");
        String sql = "INSERT INTO aims.Book "
                + "(id, discType, director, runtime, studio, subtitles, releasedDate, filmType)"
                + " VALUES "
                + queryValues.toString() + ";";
        return sql;
    }
}
