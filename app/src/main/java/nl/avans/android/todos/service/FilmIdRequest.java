package nl.avans.android.todos.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmIdMapper;
import nl.avans.android.todos.presentation.FilmDetailActivity;

/**
 * Created by maartje on 29-6-2017.
 */

public class FilmIdRequest extends AppCompatActivity {
    private Context context;
    public final String TAG = this.getClass().getSimpleName();
    int userid;


    private FilmIdRequest.FilmIdlistener listener;



    public FilmIdRequest(Context context, FilmDetailActivity listener){
        this.context = context;
        this.listener = listener;

    }

    public void handleGetAllFilmsId(int film_id) {

        Log.i(TAG, "handleGetMovies");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    Config.URL_Films + film_id,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmIdMapper.mapFilmIDList(response);
                            listener.onFilmsIdAvailable(result);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // handleErrorResponse(error);
                            Log.e(TAG, error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("X-Access-Token", token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }

    public void handlePostFilm(final Film newFilm) {

        Log.i(TAG, "handlePostMovie");

        // Haal het token uit de prefs
        // TODO Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            //
            // Maak een JSON object met username en password. Dit object sturen we mee
            // als request body (zoals je ook met Postman hebt gedaan)
            //
            String d = newFilm.getReturn_date();

            String body = "{\"returndate\":\"" + d +  "\",\"staffid\":" + 1 +  "}";

            sharedPref = context.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            userid = sharedPref.getInt(context.getString(R.string.id), userid);

            try {



                JSONObject jsonBody = new JSONObject(body);
                Log.i(TAG, "handlePostToDo - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Config.URL_POSTRENTAL + userid + "/" + newFilm.getInventory_id(),
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                // Het toevoegen is gelukt
                                // Hier kun je kiezen: of een refresh door de hele lijst op te halen
                                // en de ListView bij te werken ... Of alleen de ene update toevoegen
                                // aan de ArrayList. Wij doen dat laatste.
                                listener.onFilmAvailable(newFilm);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error - send back to caller
                                listener.onFilmsError(error.toString());
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("X-Access-Token", token);
                        return headers;
                    }
                };

                // Access the RequestQueue through your singleton class.
                VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                listener.onFilmsError(e.getMessage());
            }
        }
    }



    public interface FilmIdlistener{
        void onFilmsIdAvailable(ArrayList<Film> films);

        void onFilmAvailable(Film film);
        void onFilmsError(String message);



    }
}
