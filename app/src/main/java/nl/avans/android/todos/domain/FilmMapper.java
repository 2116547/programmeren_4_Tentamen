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
    public static final String FILM_ID = "film_id";
    public static final String FILM_RESULT = "result";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    public static final String RELEASE_YEAR = "release_year";
    public static final String LANGUAGE_ID = "language_id";
    public static final String ORIGINALLANGUAGE = "original_language_id";
    public static final String RENTAL_DURATION = "rental_duration";
    public static final String RENTAL_RATE = "rental_rate";
    public static final String LENGTH = "length";
    public static final String REPLACEMENT_COST = "replacement_cost";
    public static final String RATING = "rating";
    public static final String SPECIAL_FEATURES = "special_features";
    public static final String LAST_UPDATE = "last_update";
    public static final String INVENTORY_ID = "inventory_id";

    public static ArrayList<Film> mapFilmList(JSONObject response){
        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(FILM_RESULT);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Film film = new Film(
                        jsonObject.getInt(FILM_ID),
                        jsonObject.getString(FILM_TITLE),
                        jsonObject.getString(FILM_DESCRIPTION),
                        jsonObject.getInt(RELEASE_YEAR),
                        jsonObject.getInt(LANGUAGE_ID),

                        jsonObject.getInt(RENTAL_DURATION),
                        jsonObject.getInt(RENTAL_RATE),
                        jsonObject.getInt(LENGTH),
                        jsonObject.getInt(REPLACEMENT_COST),
                        jsonObject.getString(RATING),
                        jsonObject.getString(SPECIAL_FEATURES),
                        jsonObject.getString(LAST_UPDATE)
                );

                result.add(film);
            }
        } catch (JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONExecption " + ex.getLocalizedMessage());
        }
        return result;
    }
}
