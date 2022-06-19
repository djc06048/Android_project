package edu.skku.map.finalproject2;

import java.util.ArrayList;

public class MovieDataModel {
    private String title;
    private String original_title;
    private String backdrop_path;
    private String poster_path;
    private String release_date;
    private ArrayList<Integer> genre_ids;
    private String overview;
    private String btn;
    String id;

    public MovieDataModel(String id, String poster_path, String title, ArrayList<Integer> genre_ids, String release_date, String btn) {
        this.id=id;
        this.poster_path = poster_path;
        this.title = title;
        this.genre_ids = genre_ids;
        this.release_date = release_date;
        this.btn = btn;
    }
    public MovieDataModel(String id, String poster_path){
        this.id=id;
        this.poster_path=poster_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
