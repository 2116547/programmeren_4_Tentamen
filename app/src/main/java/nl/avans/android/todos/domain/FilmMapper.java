package nl.avans.android.todos.domain;

import android.util.Log;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Deze class vertaalt JSON objecten naar (lijsten van) ToDos.
 */
public class FilmMapper {
    public static final String FilmTitle = "title";
    public static final String Year = "release_year";
    public static final String Rating = "rating";
    public static final String Film_Result = "result";
    public static final String FilmId = "film_id";
    public static final String filmLenght = "length";
    public static final String filmDescription = "description";

    public static ArrayList<Film> mapFilmList(JSONObject response){
        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(Film_Result);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Film film = new Film(
                        jsonObject.getString(FilmTitle),
                        jsonObject.getString(Rating),
                        jsonObject.getString(Year),
                        jsonObject.getString(FilmId),
                        jsonObject.getString(filmLenght),
                        jsonObject.getString(filmDescription)
                );

                result.add(film);
            }
        } catch (JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONExecption " + ex.getLocalizedMessage());
        }
        return result;
    }
}
