package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.FileNameMap;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;
import nl.avans.android.todos.interfaces.RequestInterface;

import static nl.avans.android.todos.R.id.fab;
import static nl.avans.android.todos.presentation.MainActivity.FILM_DATA;

/**
 * Created by maartje on 19-6-2017.
 */

public class FilmDetailActivity  extends AppCompatActivity implements RequestInterface {
    private TextView textTitle, textYear, textRating, textFilmId, textFilmLenght;
    private Button rentButton;
    private String token;
    private TextView textFilmDescription;
    private Film film;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        textTitle = (TextView) findViewById(R.id.filmTitle);
        textFilmId = (TextView) findViewById(R.id.filmID);
        textYear = (TextView) findViewById(R.id.filmYear);
        textFilmLenght = (TextView) findViewById(R.id.filmLength);
        textRating = (TextView) findViewById(R.id.filmRating);
        textFilmDescription = (TextView) findViewById(R.id.filmDescription);
        rentButton = (Button) findViewById(R.id.rentButton);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rent movie
            }
        });

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        final Film film = (Film) extras.getSerializable(FILM_DATA);

        textTitle.setText(film.getTitle());
        textYear.setText(film.getYear());
        textRating.setText("Rating: " + film.getRating());
        textFilmId.setText("ID: " + film.getFilm_Id());
        textFilmLenght.setText("Lengte: " + film.getFilmLength() + " minuten");
        textFilmDescription.setText(film.getFilmDescription());

    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @NonNull
    private String getStringFromEditText(EditText editText){
        return editText.getText().toString();
    }

    @NonNull
    private Integer getIntegerFromEditText(EditText editText){
        return Integer.parseInt(getStringFromEditText(editText));
    }

    @NonNull
    private Double getDoubleFromEditText(EditText editText){
        return Double.parseDouble(getStringFromEditText(editText));
    }

    @Override
    public void onSuccess(String string) {
        finish();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Could not delete this movie!", Toast.LENGTH_SHORT).show();
    }
}
