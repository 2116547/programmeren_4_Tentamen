package nl.avans.android.todos.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Film;

/**
 * Created by maartje on 19-6-2017.
 */

public class FilmlistActivity extends AppCompatActivity {

    private ArrayList<Film> filmArrayList = new ArrayList<Film>();
    private ListView filmListView;
    private Button zoekKnop;
    private EditText zoekVeld;
    private String zoekURL, zoekInvoer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmlist);

        zoekKnop = (Button) findViewById(R.id.zoekKnop);
        zoekKnop.setOnClickListener((View.OnClickListener) this);
        zoekVeld = (EditText) findViewById(R.id.zoekVak);

        filmListView = (ListView) findViewById(R.id.filmList);
        filmListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);


    }

    public void onClick(View view){

        zoekInvoer = zoekVeld.getText().toString();

        if(!zoekInvoer.matches("")) {

            filmArrayList.clear();
            //albumAdapter.notifyDataSetChanged();
            zoekInvoer = zoekVeld.getText().toString();
            zoekURL = "http://localhost:3000/api/v1/films/" + zoekInvoer;
            zoekVeld.setText("");
            //zoekFilms(zoekURL);

        }
    }


    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
        Log.d("Geselecteerd album: ", position + "");
        Film film = (Film) filmArrayList.get(position);
        Intent intent = new Intent(getApplicationContext(), FilmDetailActivity.class);
        intent.putExtra("FILM", film);
        startActivity(intent);
    }
}