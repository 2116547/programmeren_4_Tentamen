package nl.avans.android.todos.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class Film implements Serializable {

    private String title;
    private String year;
    private String rating;
    private int film_id;


    public Film(String title, String year, String rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.film_id = film_id;
    }

   // public Film(String string, String jsonObjectString, String rating){}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public int getFilm_Id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }


    @Override
    public String toString() {
        return "Film{" + "title='" + title + '\'' + ", year='" + year + '\'' +
                ", rating='" + rating + '\'' + ", film Id='" + film_id + '}';
    }
}