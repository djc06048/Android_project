package edu.skku.map.finalproject2;

import java.util.List;

public class DbModel {
    private List<DbModel2> db;
    public DbModel(List<DbModel2> movies){
        this.db=db;
    }

    public List<DbModel2> getMovies() {
        return db;
    }

    public void setMovies(List<DbModel2> movies) {
        this.db = db;
    }
}
class DbModel2 {
    private String Homepage;
    private String genre;
    private String release_date;
    private String runtime;
    private String title;

    public String getHomepage() {
        return Homepage;
    }

    public void setHomepage(String homepage) {
        Homepage = homepage;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
