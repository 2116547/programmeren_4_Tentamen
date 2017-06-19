package nl.avans.android.todos.domain;

import android.util.Log;

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
    public static final String FilmTitle = "Titel";
    public static final String Year = "release_year";
    public static final String Rating = "rating";
    public static final String Film_Result = "result";

    public static ArrayList<Film> mapFilmList(JSONObject response){
        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(Film_Result);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Film film = new Film(
                        jsonObject.getString(FilmTitle),
                        jsonObject.getString(Rating),
                        jsonObject.getString(Year)
                );

                result.add(film);
            }
        } catch (JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONExecption " + ex.getLocalizedMessage());
        }
        return result;
    }
}
