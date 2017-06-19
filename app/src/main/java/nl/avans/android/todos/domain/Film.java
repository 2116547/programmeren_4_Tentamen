package nl.avans.android.todos.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class Film implements Serializable {

    private String title;
    private Integer year;
    private String rating;


    public Film(String title, Integer year, String rating) {
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    public Film(String string, String jsonObjectString, String rating){}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "Film{" + "title='" + title + '\'' + ", year='" + year + '\'' +
                ", rating='" + rating + '}';
    }
}