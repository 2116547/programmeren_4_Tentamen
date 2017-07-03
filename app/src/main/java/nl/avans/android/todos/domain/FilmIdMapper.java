package nl.avans.android.todos.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maartje on 29-6-2017.
 */

public class FilmIdMapper {
    // De JSON attributen die we uitlezen
    public static final String FILM_ID = "film_id";
    public static final String FILM_RESULT = "result";
    public static final String FILM_TITLE = "title";
    public static final String INVENTORY_ID = "inventory_id";
    public static final String RENTAL_id = "rental_id";
    public static final String RETURN_DATE = "return_date";
    public static final String RENTAL_DURATION = "rental_duration";
    public static final String RENTAL_RATE = "rental_rate";



    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<Film> mapFilmIDList(JSONObject response){

        ArrayList<Film> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray(FILM_RESULT);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                //String timestamp = jsonObject.getString(TODO_UPDATED_AT);
                //DateTime todoDateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(timestamp);



                Film film = new Film(
                        jsonObject.getInt(FILM_ID),
                        jsonObject.getString(FILM_TITLE),
                        jsonObject.getInt(INVENTORY_ID),
                        jsonObject.optInt(RENTAL_id),
                        jsonObject.getString(RETURN_DATE),
                        jsonObject.getInt(RENTAL_DURATION),
                        jsonObject.getInt(RENTAL_RATE)
                        //       jsonObject.getString(TODO_STATUS),
                        //todoDateTime
                );
                // Log.i("ToDoMapper", "ToDo: " + toDo);
                result.add(film);
                Log.d("film", film.getTitle());
            }
        } catch( JSONException ex) {
            Log.e("MovieMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
