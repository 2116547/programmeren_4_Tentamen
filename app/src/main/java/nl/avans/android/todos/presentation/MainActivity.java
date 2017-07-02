package nl.avans.android.todos.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmAdapter;
import nl.avans.android.todos.service.FilmRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener,
        FilmRequest.FilmListener {

    // Logging tag
    public final String TAG = this.getClass().getSimpleName();

    // The name for communicating Intents extras
    public final static String FILM_DATA = "FILMS";

    // A request code for returning data from Intent - is supposed to be unique.
    public static final int MY_REQUEST_CODE = 1234;

    // UI Elements
    private ListView listViewFilms;
    private BaseAdapter FilmAdapter;
    private ArrayList<Film> films = new ArrayList<>();
    int customerId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We kijken hier eerst of de gebruiker nog een geldig token heeft.
        // Het token is opgeslagen in SharedPreferences.
        // Mocht er geen token zijn, of het token is expired, dan moeten we
        // eerst opnieuw inloggen.
        intent = getIntent();

        //customerId = (Integer) int.getIntExtra( "id", customerId);

        setContentView(R.layout.activity_filmlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewFilms = (ListView) findViewById(R.id.filmList);
        listViewFilms.setOnItemClickListener(this);
        FilmAdapter = new FilmAdapter(this, getLayoutInflater(), films);
        listViewFilms.setAdapter(FilmAdapter);
        //
        // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
        // geldig is; dat doen we nu niet.
        // Vul de lijst met Films
        //
        Log.d(TAG, "Token gevonden - Films ophalen!");
        getFilms();
    }

    /**
     * Aangeroepen door terugkerende Intents. Maakt het mogelijk om data
     * terug te ontvangen van een Intent.
     *
     * @param requestCode
     * @param resultCode
     * @param pData
     */
//    protected void onActivityResult(int requestCode, int resultCode, Intent pData)
//    {
//        if ( requestCode == MY_REQUEST_CODE )
//        {
//            Log.v( TAG, "onActivityResult OK" );
//            if (resultCode == Activity.RESULT_OK )
//            {
//                final ToDo newToDo = (ToDo) pData.getSerializableExtra(TODO_DATA);
//                Log.v( TAG, "Retrieved Value newToDo is " + newToDo);
//
//                // We need to save our new ToDo
//                postTodo(newToDo);
//            }
//        }
//
//    }

    /**
     * Check of een token in localstorage is opgeslagen. We checken niet de geldigheid -
     * alleen of het token bestaat.
     *
     * @return
     */
    private boolean tokenAvailable() {
        boolean result = false;

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.saved_token), "dummy default token");
        if (token != null && !token.equals("dummy default token")) {
            result = true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Film film = films.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra(FILM_DATA, film);
        startActivity(intent);
    }

    /**
     * Callback function - handle an ArrayList of Films
     *
     * @param filmArrayList
     */
    @Override
    public void onFilmsAvailable(ArrayList<Film> filmArrayList) {

        Log.i(TAG, "We hebben " + filmArrayList.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            films.add(filmArrayList.get(i));
        }
        FilmAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function - handle a single Film
     *
     * @param film
     */
    @Override
    public void onFilmsAvailable(Film film) {
        films.clear();
        films.add(film);
        FilmAdapter.notifyDataSetChanged();
    }

    /**
     * Callback function
     *
     * @param message
     */
    @Override
    public void onToDosError(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start the activity to GET all the films from the server.
     */
    private void getFilms(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetFilms();
    }

    /**
     * Start the activity to POST a new ToDo to the server.
     */
//    private void postTodo(ToDo todo){
//        ToDoRequest request = new ToDoRequest(getApplicationContext(), this);
//        request.handlePostToDo(todo);
//    }
}
