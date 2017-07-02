package nl.avans.android.todos.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.FileNameMap;
import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.domain.FilmAdapter;
import nl.avans.android.todos.domain.RentalAdapter;
import nl.avans.android.todos.interfaces.RequestInterface;
import nl.avans.android.todos.service.FilmIdRequest;

import static nl.avans.android.todos.R.id.fab;
import static nl.avans.android.todos.presentation.MainActivity.FILM_DATA;

/**
 * Created by maartje on 19-6-2017.
 */

public class FilmDetailActivity  extends AppCompatActivity implements AdapterView.OnItemClickListener, FilmIdRequest.FilmIdlistener{

    public final String TAG = this.getClass().getSimpleName();


    public final static String FILM_DATA = "FILMS";

    public static final int MY_REQUEST_CODE = 1234;

    private ListView listView;
    private BaseAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();
    private int film_id;
    private TextView status;

    private TextView title, desription, release, rating;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        title = (TextView) findViewById(R.id.filmTitle);
        rating = (TextView) findViewById(R.id.filmRating);
       // status = (TextView) findViewById(R.id.available);

        listView = (ListView) findViewById(R.id.filmDetaillist);
        listView.setOnItemClickListener(this);

        filmAdapter = new RentalAdapter(this, films);
        listView.setAdapter(filmAdapter);

        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(FILM_DATA);
        Log.d(TAG, film.toString());

        film_id = film.getFilm_id();

        title.setText(film.getTitle());
        rating.setText(film.getRating());

        getFilms();



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "Position " + position + " is geselecteerd");
        Film film = films.get(position);
        Log.d(TAG, "" + film.getRental_id());
        if (film.getRental_id() != 0) {
            Log.d(TAG, "movie not saved");
        }else{

            FilmIdRequest request = new FilmIdRequest(getApplicationContext(), this);
            request.handlePostFilm(film);
            filmAdapter.notifyDataSetChanged();

            Context context = getApplicationContext();
            CharSequence text = "Movie: " + film.getTitle() + " saved";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            getFilms();
        }




    }

   @Override
    public void onFilmsIdAvailable(ArrayList<Film> filmArrayList) {

        Log.i(TAG, "We hebben " + filmArrayList.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            films.add(filmArrayList.get(i));
        }
        filmAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFilmAvailable(Film film) {

    }

    @Override
    public void onFilmsError(String message) {

    }

    private void getFilms(){
        FilmIdRequest request = new FilmIdRequest(getApplicationContext(), this);
        request.handleGetAllFilmsId(film_id);
    }

}
