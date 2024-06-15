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
    String subtitle;
    Date releasedDate;
    String filmType;

    public DVD() throws SQLException{

    }

    public DVD(int id, String title, String category, int price, int quantity, String type, String discType,
            String director, int runtime, String studio, String language, String subtitle, Date releasedDate, String filmType) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitle = subtitle;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
    }

    public DVD(int id, String title, String category, int price, int value, int quantity, String type, float weight, String imageURL, String discType,
               String director, int runtime, String studio, String language, String subtitle, Date releasedDate, String filmType, int supportForRushDelivery) throws SQLException{
        super(id, title, category, price, value, quantity, weight, type, imageURL, supportForRushDelivery);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.language = language;
        this.subtitle = subtitle;
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
    
    public String getLanguage() {
        return this.language;
    }

    public DVD setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public DVD setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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
            String language = res.getString("language");
            String subtitle = res.getString("subtitle");
            Date releasedDate = res.getDate("releasedDate");
            String filmType = res.getString("filmType");

            return new DVD(id, title, category, price, value, quantity, type, weight, imageUrl, discType, director, 
            		runtime, studio, language, subtitle, releasedDate, filmType, supportForRushDelivery);        
        } else {
            throw new SQLException();
        }
    }
   
    
    public String toString() {
        return "{" +
            super.toString() +
            " discType='" + discType + "'" +
            ", director='" + director + "'" +
            ", runtime='" + runtime + "'" +
            ", studio='" + studio + "'" +
            ", language='" + language + "'" +
            ", subtitle='" + subtitle + "'" +
            ", releasedDate='" + releasedDate + "'" +
            ", filmType='" + filmType + "'" +
            "}";
    }

    public String createDVDQuery(String discType, String director, int runtime, String studio, String language, String subtitles, String releasedDate, String filmType) throws SQLException {
        StringBuilder queryValues = new StringBuilder();
        queryValues.append("(")
                .append("placeForId").append(", ")
                .append("'").append(discType).append("'").append(", ")
                .append("'").append(director).append("'").append(", ")
                .append(runtime).append(", ")
                .append("'").append(studio).append("'").append(", ")
                .append("'").append(language).append("'").append(", ")
                .append("'").append(subtitle).append("'").append(", ")
                .append("'").append(releasedDate).append("'").append(", ")
                .append("'").append(filmType).append("'").append(")");
        String sql = "INSERT INTO DVD "
                + "(id, discType, director, runtime, studio, language, subtitle, releasedDate, filmType)"
                + " VALUES "
                + queryValues.toString() + ";";
        return sql;
    }

    public void deleteMediaFieldById(int id) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("DELETE FROM " + "DVD" + " WHERE id = " + id + ";");
    }
}
