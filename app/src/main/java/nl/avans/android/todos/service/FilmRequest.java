package nl.avans.android.todos.service;

import android.content.Context;
import android.content.SharedPreferences;
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
import nl.avans.android.todos.domain.FilmMapper;
import nl.avans.android.todos.domain.RentalMapper;

/**
 * Created by maartje on 19-6-2017.
 */

public class FilmRequest {
    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private FilmRequest.FilmListener listener;
    private int userid;
    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public FilmRequest(Context context, FilmRequest.FilmListener listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * Verstuur een GET request om alle Films op te halen.
     */
    public void handleGetFilms() {

        Log.i(TAG, "handleGetFilms");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://programmeren-4-tentamen.herokuapp.com/api/v1/film",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmMapper.mapFilmList(response);
                            listener.onFilmsAvailable(result);
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
                    headers.put("Authorization", token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }

    public void handlePostRental() {

        Log.i(TAG, "handlePostrental");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://programmeren-4-practicum-server.herokuapp.com/api/v1/rentals/",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmMapper.mapFilmList(response);
                            listener.onFilmsAvailable(result);
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
                    headers.put("Authorization", token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }



    /**
     * Verstuur een POST met nieuwe ToDo.
     */
//    public void handlePostToDo(final ToDo newTodo) {
//
//        Log.i(TAG, "handlePostToDo");
//
//        // Haal het token uit de prefs
//        // TODO Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
//        if(token != null && !token.equals("dummy default token")) {
//
//            //
//            // Maak een JSON object met username en password. Dit object sturen we mee
//            // als request body (zoals je ook met Postman hebt gedaan)
//            //
//            String body = "{\"Titel\":\"" + newTodo.getTitle() + "\",\"Beschrijving\":\"" + newTodo.getContents() + "\"}";
//
//            try {
//                JSONObject jsonBody = new JSONObject(body);
//                Log.i(TAG, "handlePostToDo - body = " + jsonBody);
//                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
//                        Request.Method.POST,
//                        Config.URL_TODOS,
//                        jsonBody,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.i(TAG, response.toString());
//                                // Het toevoegen is gelukt
//                                // Hier kun je kiezen: of een refresh door de hele lijst op te halen
//                                // en de ListView bij te werken ... Of alleen de ene update toevoegen
//                                // aan de ArrayList. Wij doen dat laatste.
//                                listener.onToDoAvailable(newTodo);
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // Error - send back to caller
//                                listener.onToDosError(error.toString());
//                            }
//                        }){
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("Content-Type", "application/json");
//                        headers.put("Authorization", "Bearer " + token);
//                        return headers;
//                    }
//                };

    // Access the RequestQueue through your singleton class.
//                VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
//            } catch (JSONException e) {
//                Log.e(TAG, e.getMessage());
//                listener.onToDosError(e.getMessage());
//            }
//        }
//    }

    public void handleGetAllRentalMovies(int customer_id) {



        Log.i(TAG, "handleGetRentalMovies");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    Config.URL_RENTALFILMS + customer_id,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = RentalMapper.mapMovieList(response);
                            listener.onRentalFilmsAvailable(result);
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

    public void handleDelMovie(final Film newFilm) {

        Log.i(TAG, "handlePostMovie");

        // Haal het token uit de prefs
        // TODO Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if(token != null && !token.equals("dummy default token")) {
            String body = "{\"Titel\":\"" + newFilm.getTitle() + "\",\"Beschrijving\":\"" + newFilm.getDescription() + "\"}";
            sharedPref = context.getSharedPreferences(
                    context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            userid = sharedPref.getInt(context.getString(R.string.id), userid);

            try {
                JSONObject jsonBody = new JSONObject(body);
                Log.i(TAG, "handleDelmovie - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.DELETE,
                        Config.URL_POSTRENTAL + userid + "/" + newFilm.getInventory_id(),
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());

                                listener.onMovieDelAvailable();
                                Log.d(TAG, Config.URL_POSTRENTAL + userid + "/" + newFilm.getInventory_id() );
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error - send back to caller
                                listener.onMoviesError(error.toString());
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
                listener.onMoviesError(e.getMessage());
            }
        }
    }
    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //
    public interface FilmListener {
        // Callback function to return a fresh list of Films
        void onFilmsAvailable(ArrayList<Film> films);
        void onMoviesError(String message);

        void onRentalFilmsAvailable(ArrayList<Film> films);
        void onRentalMovieAvailable(Film film);
        void onRentalMoviesError(String message);

        // Callback function to handle a single added Film.
        void onFilmsAvailable(Film film);

        // Callback to handle serverside API errors
        void onToDosError(String message);

        void onMovieDelAvailable();
        void onMoviesDelError(String message);
    }
}
