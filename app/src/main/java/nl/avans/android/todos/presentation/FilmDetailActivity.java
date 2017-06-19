package nl.avans.android.todos.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;

import static nl.avans.android.todos.presentation.MainActivity.TODO_DATA;

/**
 * Created by maartje on 19-6-2017.
 */

public class FilmDetailActivity  extends AppCompatActivity {
    private TextView textTitle;
    private TextView textYear;
    private TextView textRating;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        textTitle = (TextView) findViewById(R.id.textDetailToDoTitle);


        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(TODO_DATA);
        Log.i(TAG, film.toString());

        textTitle.setText(film.getTitle());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // or go to another activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
