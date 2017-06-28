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
    private TextView textTitle;
    private TextView textYear;
    private TextView textRating;
    private TextView textFilmId;
    private TextView textFilmLenght;
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

        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();

        final Film film = (Film) extras.getSerializable(FILM_DATA);

       textTitle.setText(film.getTitle());
       textYear.setText(film.getYear());
       textRating.setText(film.getRating());
       textFilmId.setText(film.getFilm_Id());
       textFilmLenght.setText(film.getFilmLength());
       textFilmDescription.setText(film.getFilmDescription());



   //     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(film.getTitle());
     //   setSupportActionBar(toolbar);




    }



    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  if(film.getFilmId() != 0) {
        //    getMenuInflater().inflate(R.menu.menu_detail, menu);
        //}
        //return true;
    //}

 //   @Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_delete) {
          //  RESTApiV1 restApiV1 = new RESTApiV1(this);
            //restApiV1.deleteMovie(movie, token, this);
            //return true;
        //}

        //return super.onOptionsItemSelected(item);
    //}



   // @Override
    //public void onClick(View view) {
      //  switch (view.getId()){
        //    case R.id.fab:
          //      handleFabClick();
            //    break;
        //}
    //}


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
